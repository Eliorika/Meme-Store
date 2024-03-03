package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.dto.asset.AssetUploadRequest;
import dev.chipichapa.memestore.dto.asset.AssetUploadResponse;
import dev.chipichapa.memestore.dto.asset.SuggestionsTagsResponse;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.s3storage.dto.File;
import dev.chipichapa.memestore.service.ifc.FileService;
import dev.chipichapa.memestore.service.ifc.ImageProcessingService;
import dev.chipichapa.memestore.service.ifc.UserService;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AssetsUseCaseImpl implements AssetsUseCase {

    private final AuthUtils authUtils;
    private final UserService userService;
    private final FileService fileService;
    private final ImageRepository imageRepository;

    private final ImageProcessingService imageProcessingService;

    @Override
    @SneakyThrows
    public AssetUploadResponse upload(AssetUploadRequest uploadRequest) {
        UserDetails userDetails = authUtils.getUserDetailsOrThrow();
        User user = userService.getByUsername(userDetails.getUsername());

        MultipartFile file = uploadRequest.getFile();
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());

        Image savingAsset = new Image()
                .setStatus("public")
                .setAuthor(user)
                .setExtension(fileExtension)
                .setUuid(UUID.randomUUID());

        imageRepository.save(savingAsset);
        fileService.save(new File(getFilenameWithExtension(savingAsset), file.getBytes()));

        Integer assetId = savingAsset.getId();
        return new AssetUploadResponse(String.valueOf(assetId));
    }

    @Override
    public SuggestionsTagsResponse getSuggestTags(String assetTicket) {
        Image asset = imageRepository.findById(Long.valueOf(assetTicket))
                .orElseThrow(
                        () -> new ResourceNotFoundException("Asset with this ticket is not found")
                );

        String filenameWithExtension = getFilenameWithExtension(asset);
        File file = fileService.get(filenameWithExtension);

        Set<String> tags = imageProcessingService.predictTags(file.getContent());
        return new SuggestionsTagsResponse(tags);
    }

    private String getFilenameWithExtension(Image asset) {
        return asset.getId() + "." + asset.getExtension();
    }
}
