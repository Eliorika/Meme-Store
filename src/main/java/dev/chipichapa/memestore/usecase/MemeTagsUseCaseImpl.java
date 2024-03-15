package dev.chipichapa.memestore.usecase;

import dev.chipichapa.memestore.domain.entity.*;
import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.enumerated.RecommendationMarks;
import dev.chipichapa.memestore.domain.enumerated.VoteType;
import dev.chipichapa.memestore.domain.model.tag.MemeTag;
import dev.chipichapa.memestore.dto.recommedation.MarkRabbitDTO;
import dev.chipichapa.memestore.dto.tags.GetMemeTagsResponse;
import dev.chipichapa.memestore.dto.tags.VoteMemeTagResponse;
import dev.chipichapa.memestore.exception.AppException;
import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.repository.AlbumRepository;
import dev.chipichapa.memestore.repository.ImageTagRepository;
import dev.chipichapa.memestore.repository.UserTagVoteRepository;
import dev.chipichapa.memestore.service.ifc.ImageService;
import dev.chipichapa.memestore.service.ifc.RecommendationRabbitProducer;
import dev.chipichapa.memestore.service.ifc.TagService;
import dev.chipichapa.memestore.service.ifc.UserService;
import dev.chipichapa.memestore.usecase.ifc.MemeTagsUseCase;
import dev.chipichapa.memestore.utils.AuthUtils;
import dev.chipichapa.memestore.utils.mapper.ImageTagsAndTagVotesToMemeTagMapper;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemeTagsUseCaseImpl implements MemeTagsUseCase {

    private final ImageService imageService;
    private final UserService userService;
    private final TagService tagService;

    private final ImageTagRepository imageTagRepository;
    private final AlbumRepository albumRepository;
    private final UserTagVoteRepository userTagVoteRepository;

    private final AuthUtils authUtils;
    private final ImageTagsAndTagVotesToMemeTagMapper imageTagsAndTagVotesToMemeTagMapper;

    private final RecommendationRabbitProducer recommendationRabbitProducer;

    @Override
    public GetMemeTagsResponse getMemeTags(Long memeId, Long galleryId) {
        User user = getUserFormAuth();

        Image image = imageService.getById(memeId);
        checkImageContainsInAlbumOrThrow(galleryId, memeId);
        List<ImageTag> imageTags = imageTagRepository.findByImage(image);

        List<UserTagVote> userTagVotes = userTagVoteRepository
                .findUserTagVotesByUserAndImageAndTagIn(user, image, getTagList(imageTags));

        List<MemeTag> result = imageTagsAndTagVotesToMemeTagMapper.toList(imageTags, userTagVotes);
        return new GetMemeTagsResponse(result);
    }

    @Override
    public VoteMemeTagResponse voteMemeTag(Long memeId, Long tagId, @Nullable VoteType type) {
        User user = getUserFormAuth();
        Tag tag = tagService.getById(tagId);
        Image image = imageService.getById(memeId);

        userVoteProcess(type, user, image, tag);

        List<ImageTag> imageTags = imageTagRepository.findByImage(image);
        List<UserTagVote> userTagVotes = userTagVoteRepository
                .findUserTagVotesByUserAndImageAndTagIn(user, image, getTagList(imageTags));

        List<MemeTag> result = imageTagsAndTagVotesToMemeTagMapper.toList(imageTags, userTagVotes);

        recommendationRabbitProducer.sendMark(new MarkRabbitDTO(
                image.getAuthor().getId(),
                image.getId(),
                RecommendationMarks.CHANGE_TAGS_MEME.getMark()
        ));

        return new VoteMemeTagResponse(result);
    }

    private void userVoteProcess(@Nullable VoteType type, User user, Image image, Tag tag) {
        boolean voteIsExists = userTagVoteRepository
                .existsById(new UserTagVotePK(user, image, tag));

        if (!voteIsExists) {
            UserTagVote voteTemplate = constructVoteTemplate(user, tag, image);

            if (type == null) {
                throw new AppException("U can't delete your vote because your didn't votes before");
            }
            voteTemplate.setType(type);
            userTagVoteRepository.save(voteTemplate);
        } else {
            UserTagVote userTagVote = getUserTagVote(user, image, tag);
            if (type == null) {
                userTagVoteRepository.delete(userTagVote);
            } else {
                if (!isSimilarTypes(type, userTagVote)) {
                    userTagVote.setType(type);
                    userTagVoteRepository.save(userTagVote);
                }
            }
        }
    }


    private UserTagVote constructVoteTemplate(User user, Tag tag, Image image) {
        return new UserTagVote()
                .setUser(user)
                .setTag(tag)
                .setImage(image);
    }

    @NotNull
    private UserTagVote getUserTagVote(User user, Image image, Tag tag) {
        return userTagVoteRepository
                .findById(new UserTagVotePK(user, image, tag)).get();
    }

    private List<Tag> getTagList(List<ImageTag> imageTags) {
        List<Tag> tags = imageTags
                .stream()
                .map(ImageTag::getTag)
                .collect(Collectors.toList());
        return tags;
    }

    private boolean isSimilarTypes(VoteType type, UserTagVote userTagVote) {
        return userTagVote.getType().getValue().equals(type.getValue());
    }

    private void checkImageContainsInAlbumOrThrow(Long albumId, Long imageId) {
        if (!albumIsContainsImage(albumId, imageId)) {
            throw new ResourceNotFoundException("Album is not contains this image");
        }
    }

    private boolean albumIsContainsImage(Long albumId, Long imageId) {
        return albumRepository.existsImageById(albumId, imageId);
    }

    private User getUserFormAuth() {
        UserDetails userDetails = authUtils.getUserDetailsOrThrow();
        User user = userService.getByUsername(userDetails.getUsername());
        return user;
    }
}
