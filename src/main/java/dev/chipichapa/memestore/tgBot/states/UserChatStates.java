package dev.chipichapa.memestore.tgBot.states;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserChatStates {
    private Map<Long, UserState> mapStates = new HashMap<>();
    private Map<Long, Album> mapAlbums = new HashMap<>();
    private Map<Long, CreateMemeRequest> mapMemes = new HashMap<>();


    public void addUser(long id, UserState state){
        mapStates.put(id, state);
        if(UserState.NO_ACTION.equals(state)){
            mapAlbums.remove(id);
        }
    }

    public UserState getUserState(long id){
        return mapStates.get(id);
    }

    public void addUserAlbum(long id, Album album){
        mapAlbums.put(id, album);
    }

    public Album getUserAlbum(long id){
        return mapAlbums.get(id);
    }

    public void addUserMeme(long id, CreateMemeRequest req){
        mapMemes.put(id, req);
    }

    public CreateMemeRequest getUserMeme(long id){
        return mapMemes.get(id);
    }
}
