package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.Draft;
import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.enumerated.AssetType;
import dev.chipichapa.memestore.dto.asset.*;
import dev.chipichapa.memestore.exception.AppException;
import dev.chipichapa.memestore.repository.DraftRepository;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.s3storage.dto.File;
import dev.chipichapa.memestore.service.ifc.FileService;
import dev.chipichapa.memestore.service.ifc.ImageProcessingService;
import dev.chipichapa.memestore.service.ifc.ImageService;
import dev.chipichapa.memestore.service.ifc.UserService;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import dev.chipichapa.memestore.utils.FileUtils;
import dev.chipichapa.memestore.utils.ImageUtils;
import io.trbl.blurhash.BlurHash;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AssetsUseCaseImpl implements AssetsUseCase {

    private final AuthUtils authUtils;

    private final UserService userService;
    private final FileService fileService;
    private final ImageService imageService;
    private final ImageProcessingService imageProcessingService;

    private final ImageRepository imageRepository;
    private final DraftRepository draftRepository;

    private final ThreadPoolTaskExecutor executor;

    @Value("${file-storage.url}")
    private String imageStorageUrl;

    @Override
    @SneakyThrows
    @Transactional
    public AssetUploadResponse upload(AssetUploadRequest uploadRequest) {
        UserDetails userDetails = authUtils.getUserDetailsOrThrow();
        User user = userService.getByUsername(userDetails.getUsername());

        MultipartFile file = uploadRequest.getFile();
        if (file == null)
            throw new IllegalArgumentException("Missing file body");

        var fileType = FileUtils.getRealMimeType(file.getBytes());
        String fileExtension = FileUtils.ALLOWED_MIME_EXTENSIONS.get(fileType);

        if (fileExtension == null)
            throw new IllegalArgumentException("Wrong extension");

        UUID uuid = UUID.randomUUID();

        Image savingAsset = new Image()
                .setAuthor(user)
                .setExtension(fileExtension)
                .setUuid(uuid);

        draftRepository.save(new Draft().setId(uuid));
        imageRepository.save(savingAsset);

        executor.execute(() -> {
            calculateBlurHashForImageAndSave(file, savingAsset);
        });

        fileService.save(new File(savingAsset.getFilenameWithExtension(), file.getBytes()));

        return new AssetUploadResponse(String.valueOf(uuid));
    }

    @Override
    @Transactional
    public AssetGetResponse getById(Long id) {
        Image asset = imageService.getById(id);
        File file = fileService.get(asset.getFilenameWithExtension());
        String fileExtension = FilenameUtils.getExtension(file.getFilename());

        return new AssetGetResponse(file.getContent(), fileExtension);
    }

    @Override
    @Transactional
    public AssetGetResponse get(String assetTicket) {
        File file = getFileByAssetTicket(assetTicket);
        String fileExtension = FilenameUtils.getExtension(file.getFilename());

        return new AssetGetResponse(file.getContent(), fileExtension);
    }

    @Override
    public AssetGetResponse getById(long id) {
        File file = getFileById(id);
        String fileExtension = FilenameUtils.getExtension(file.getFilename());

        return new AssetGetResponse(file.getContent(), fileExtension);
    }

    @Override
    @Transactional
    public SuggestionsTagsResponse getSuggestTags(String assetTicket) {
        File file = getFileByAssetTicket(assetTicket);

        Set<String> tags = imageProcessingService.predictTags(file.getContent());
        return new SuggestionsTagsResponse(tags);
    }

    private File getFileByAssetTicket(String ticket) {
        Image asset = imageService.getByTicket(ticket);
        return fileService.get(asset.getFilenameWithExtension());
    }

    private File getFileById(long id) {
        Image asset = imageService.getById(id);
        return fileService.get(asset.getFilenameWithExtension());
    }

    @Override
    @Transactional
    public AssetGetInfoResponse getAssetInfoById(Integer id) {
        Image image = imageService.getById((long) id);
        return buildAssetGetInfoResponse(image);
    }

    @Override
    @Transactional
    public AssetGetInfoResponse getAssetInfoByTicket(String assetTicket) {
        Image image = imageService.getByTicket(assetTicket);
        return buildAssetGetInfoResponse(image);
    }

    private AssetGetInfoResponse buildAssetGetInfoResponse(Image image) {
        String url = imageStorageUrl + image.getUuid();
        return new AssetGetInfoResponse(image.getId(), image.getBlurhash(), AssetType.IMAGE, url);
    }

    private void calculateBlurHashForImageAndSave(MultipartFile file, Image image) {
        try {
            String hash = getHashForFile(file.getBytes());
            image.setBlurhash(hash);
            imageRepository.save(image);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new AppException(e);
        }
    }

    private String getHashForFile(byte[] file) {
        BufferedImage bfImage = ImageUtils.byteArrayToBufferedImage(file);
        return BlurHash.encode(bfImage);
    }
}
