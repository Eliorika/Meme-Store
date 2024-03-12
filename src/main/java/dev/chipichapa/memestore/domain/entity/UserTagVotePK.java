package dev.chipichapa.memestore.domain.entity;

import dev.chipichapa.memestore.domain.entity.user.User;

import java.io.Serializable;

public class UserTagVotePK implements Serializable {
    protected User user;
    protected Image image;
    protected Tag tag;

    public UserTagVotePK() {
    }

    public UserTagVotePK(User user, Image image, Tag tag) {
        this.user = user;
        this.image = image;
        this.tag = tag;
    }
}
