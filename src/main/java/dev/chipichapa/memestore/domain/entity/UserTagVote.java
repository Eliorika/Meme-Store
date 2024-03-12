package dev.chipichapa.memestore.domain.entity;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.enumerated.VoteType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Entity
@Getter
@Setter
@Table(name = "user_tag_votes", schema = "public")
@IdClass(UserTagVotePK.class)
@Accessors(chain = true)
public class UserTagVote {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Id
    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;

    @Getter
    @Column(name = "type", nullable = false)
    private Boolean type;


    public void setType(VoteType voteType) {
        if (voteType.equals(VoteType.UP)) {
            this.type = true;
        }
        if (voteType.equals(VoteType.DOWN)) {
            this.type = false;
        }
    }

    public VoteType getType() {
        if (this.type == true) {
            return VoteType.UP;
        }
        if (this.type == false) {
            return VoteType.DOWN;
        }
        return VoteType.DELETE;
    }

}
