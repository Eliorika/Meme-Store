package dev.chipichapa.memestore.repository;

import dev.chipichapa.memestore.domain.entity.Image;
import dev.chipichapa.memestore.domain.entity.Tag;
import dev.chipichapa.memestore.domain.entity.UserTagVote;
import dev.chipichapa.memestore.domain.entity.UserTagVotePK;
import dev.chipichapa.memestore.domain.entity.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTagVoteRepository extends CrudRepository<UserTagVote, UserTagVotePK> {

    List<UserTagVote> findUserTagVotesByUserAndImageAndTagIn(User user, Image image, List<Tag> tags);
}
