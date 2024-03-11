package dev.chipichapa.memestore.domain.enumerated;

public enum VoteType {
    DOWN(0), UP(1), DELETE(null);

    private final Integer value;

    VoteType(Integer value) {
        if (value == null) {
            this.value = null;
        } else {
            this.value = value;
        }
    }

    public Integer getValue() {
        return value;
    }
}
