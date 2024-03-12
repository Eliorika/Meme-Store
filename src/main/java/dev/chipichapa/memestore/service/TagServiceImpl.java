package dev.chipichapa.memestore.service;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.Tag;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.TagRepository;
import dev.chipichapa.memestore.service.ifc.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public List<Integer> addTagsToImageAndReturnTagsIds(Image image, List<String> tags) {
        Set<Tag> existingTags = getExistingTags(tags);
        Set<Tag> newTags = getNewTags(tags, existingTags);

        Set<Tag> allTags = new HashSet<>(existingTags);
        allTags.addAll(newTags);

        List<Integer> tagsIds = getAllTagsIds(allTags);
        saveTagsForImage(image, tagsIds);

        return tagsIds;
    }

    @Override
    public Tag getById(Long id) {
        return tagRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tag with this id is not found"));
    }

    private void saveTagsForImage(Image image, List<Integer> tagsIds) {
        Integer imageId = image.getId();
        for (Integer tagId : tagsIds) {
            tagRepository.saveTagForImage(imageId, tagId);
        }
    }

    private Set<Tag> getExistingTags(List<String> tags) {
        return tagRepository.findByTagIn(tags);
    }

    private Set<Tag> getNewTags(List<String> tags, Set<Tag> existingTags) {
        return tags
                .stream()
                .filter(tagString -> !existingTags.stream()
                        .map(Tag::getTag)
                        .collect(Collectors.toSet())
                        .contains(tagString)
                )
                .map(this::createAndSaveNewTag)
                .collect(Collectors.toSet());
    }

    private Tag createAndSaveNewTag(String tagString) {
        Tag tag = new Tag();
        tag.setTag(tagString);
        return tagRepository.save(tag);
    }

    private static List<Integer> getAllTagsIds(Set<Tag> allTags) {
        return allTags
                .stream()
                .map(Tag::getId)
                .toList();
    }
}
