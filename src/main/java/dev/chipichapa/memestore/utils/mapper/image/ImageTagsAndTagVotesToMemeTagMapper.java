package dev.chipichapa.memestore.utils.mapper.image;

import dev.chipichapa.memestore.domain.entity.ImageTag;
import dev.chipichapa.memestore.domain.entity.Tag;
import dev.chipichapa.memestore.domain.entity.UserTagVote;
import dev.chipichapa.memestore.domain.model.tag.MemeTag;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ImageTagsAndTagVotesToMemeTagMapper {
    private Map<Tag, UserTagVote> dictionary;

    public List<MemeTag> toList(List<ImageTag> tags, List<UserTagVote> votes) {
        initDictionary(votes);

        List<MemeTag> result = new ArrayList<>(tags.size());

        for (ImageTag imageTag : tags) {
            Tag tempTag = imageTag.getTag();
            MemeTag tag = new MemeTag(
                    tempTag.getId(),
                    tempTag.getTag(),
                    imageTag.getImage().getId(),
                    imageTag.getScore(),
                    getUserVote(tempTag));
            result.add(tag);
        }

        return result;
    }

    private void initDictionary(List<UserTagVote> votes) {
        dictionary = new HashMap<>();

        for (UserTagVote vote : votes) {
            dictionary.put(vote.getTag(), vote);
        }
    }

    public Integer getUserVote(Tag tag) {
        if (!dictionary.containsKey(tag)) {
            return null;
        }
        UserTagVote tagVote = dictionary.get(tag);

        if (tagVote == null) {
            return null;
        }

        return tagVote.getType().getValue();
    }
}
