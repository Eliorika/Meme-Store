package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.model.Meme;
import dev.chipichapa.memestore.dto.meme.SearchMemeResponse;
import dev.chipichapa.memestore.repository.ImageRepository;
import dev.chipichapa.memestore.repository.TagRepository;
import dev.chipichapa.memestore.service.ifc.SearchService;
import dev.chipichapa.memestore.typesense.dto.File;
import dev.chipichapa.memestore.usecase.ifc.SearchUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SearchUseCaseImpl implements SearchUseCase {

    private final SearchService typesenseService;
    private final TagRepository tagRepository;
    private final ImageRepository imageRepository;

    @Override
    public SearchMemeResponse search(String query) {
        List<File> files = typesenseService.getFilesByQuery(query);

        List<Meme> memes = files.stream()
                .map(file -> imageRepository.findImageIfVisibleAlbumsExist(file.id()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::mapImageToMeme)
                .toList();

        return new SearchMemeResponse(memes);
    }

    private Meme mapImageToMeme(Image image) {
        Integer imageId = image.getId();
        return new Meme(
                imageId,
                image.getAuthor().getId(),
                imageId,
                tagRepository.findTagsIdsByImageId(imageId),
                image.getTitle(),
                image.getDescription()
        );
    }
}
