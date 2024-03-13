package dev.chipichapa.memestore.tgBot.states;

public enum UserState {
    CREATING_ALBUM_NAME,
    CREATING_ALBUM_DESCRIPTION,
    CREATING_ALBUM_STATUS,

    UPLOAD_MEME,
    UPLOAD_MEME_TITLE,
    UPLOAD_MEME_DESCRIPTION,
    UPLOAD_MEME_TAGS,

    SUCCESS,
    NO_ACTION
}
