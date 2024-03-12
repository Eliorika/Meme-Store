package dev.chipichapa.memestore.tgBot.states;

import dev.chipichapa.memestore.domain.entity.Album;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserChatStates {
    private Map<Long, UserState> mapStates = new HashMap<>();
    private Map<Long, Album> mapAlbums = new HashMap<>();

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
}
