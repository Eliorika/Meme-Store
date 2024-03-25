package dev.chipichapa.memestore.tgBot.states;

import java.util.HashSet;
import java.util.Set;

public enum UserState {
    CREATING_ALBUM_NAME,
    CREATING_ALBUM_DESCRIPTION,
    CREATING_ALBUM_STATUS,

    UPLOAD_MEME,
    UPLOAD_MEME_TITLE,
    UPLOAD_MEME_DESCRIPTION,
    UPLOAD_MEME_TAGS,
    UPLOAD_MEME_GALLERY,

    GET_MEMES_GALLERY,
    GET_MEMES_SHOW,

    FIND_MEME,
    MOVE_MEME,

    DELETE_MEME,
    DELETE_GALLERY,
    DELETE_GALLERY_CHOOSE,

    SUCCESS,
    FAIL,
    NO_ACTION,
    ROUTE;

    public static boolean isCallBackIgnore(UserState userState){
        return userState == DELETE_GALLERY_CHOOSE ||
                userState == DELETE_GALLERY ||
                userState == NO_ACTION ||
                userState == MOVE_MEME ||
                userState == GET_MEMES_SHOW;
    }
}
