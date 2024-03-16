package dev.chipichapa.memestore.domain.enumerated;

public enum RecommendationMarks {
    OPEN_MEME(3),
    CHANGE_TAGS_MEME(5),
    ADD_MEME(8);

    private final int mark;

    RecommendationMarks(int mark) {
        this.mark = mark;
    }

    public int getMark() {
        return mark;
    }
}
