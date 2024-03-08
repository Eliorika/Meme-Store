package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.Draft;
import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.enumerated.AssetType;
import dev.chipichapa.memestore.dto.asset.*;
import dev.chipichapa.memestore.repository.DraftRepository;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.s3storage.dto.File;
import dev.chipichapa.memestore.service.ifc.FileService;
import dev.chipichapa.memestore.service.ifc.ImageProcessingService;
import dev.chipichapa.memestore.service.ifc.ImageService;
import dev.chipichapa.memestore.service.ifc.UserService;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import dev.chipichapa.memestore.utils.ImageUtils;
import io.trbl.blurhash.BlurHash;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetsUseCaseImpl implements AssetsUseCase {

    private final AuthUtils authUtils;

    private final UserService userService;
    private final FileService fileService;
    private final ImageService imageService;
    private final ImageProcessingService imageProcessingService;

    private final ImageRepository imageRepository;
    private final DraftRepository draftRepository;

    @Value("${file-storage.url}")
    private String imageStorageUrl;

    @Override
    @SneakyThrows
    public AssetUploadResponse upload(AssetUploadRequest uploadRequest) {
        UserDetails userDetails = authUtils.getUserDetailsOrThrow();
        User user = userService.getByUsername(userDetails.getUsername());

        MultipartFile file = uploadRequest.getFile();
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        UUID uuid = UUID.randomUUID();

        Image savingAsset = new Image()
                .setAuthor(user)
                .setExtension(fileExtension)
                .setUuid(uuid);

        draftRepository.save(new Draft().setId(uuid));
        imageRepository.save(savingAsset);
        fileService.save(new File(savingAsset.getFilenameWithExtension(), file.getBytes()));


        return new AssetUploadResponse(String.valueOf(uuid));
    }

    @Override
    public AssetGetResponse get(String assetTicket) {
        File file = getFileByAssetTicket(assetTicket);
        String fileExtension = FilenameUtils.getExtension(file.getFilename());

        return new AssetGetResponse(file.getContent(), fileExtension);
    }

    @Override
    public SuggestionsTagsResponse getSuggestTags(String assetTicket) {
        File file = getFileByAssetTicket(assetTicket);

        Set<String> tags = imageProcessingService.predictTags(file.getContent());
        return new SuggestionsTagsResponse(tags);
    }

    private File getFileByAssetTicket(String ticket){
        Image asset = imageService.getByTicket(ticket);
        return fileService.get(asset.getFilenameWithExtension());
    }

    @Override
    public AssetGetInfoResponse getAssetInfoById(Integer id) {
        Image image = imageService.getById((long) id);
        return buildAssetGetInfoResponse(image);
    }

    @Override
    public AssetGetInfoResponse getAssetInfoByTicket(String assetTicket){
        Image image = imageService.getByTicket(assetTicket);
        return buildAssetGetInfoResponse(image);
    }
    private AssetGetInfoResponse buildAssetGetInfoResponse(Image image){
        File file = fileService.get(image.getFilenameWithExtension());
        String blurhash = getHashForFile(file);
        String url = imageStorageUrl + image.getUuid();

        return new AssetGetInfoResponse(image.getId(), blurhash, AssetType.IMAGE, url);
    }

    private String getHashForFile(File file) {
        BufferedImage bfImage = ImageUtils.byteArrayToBufferedImage(file.getContent());
        return BlurHash.encode(bfImage);
    }


    private String getFilenameWithExtension(Image asset) {
        return asset.getUuid() + "." + asset.getExtension();
    }
}
