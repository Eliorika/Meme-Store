package dev.chipichapa.memestore.domain.enumerated;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum VoteType {
    DOWN(0), UP(1), DELETE(null);

    private final Integer value;

    VoteType(Integer value) {
        this.value = value;
    }

    public static VoteType valueOf(Integer value) {
        return Arrays.stream(values()).filter(voteType -> voteType.value.equals(value)).findFirst().get();
    }

}
