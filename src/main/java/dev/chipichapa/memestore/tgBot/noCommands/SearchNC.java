package dev.chipichapa.memestore.tgBot.noCommands;

import dev.chipichapa.memestore.service.ifc.SearchService;
import dev.chipichapa.memestore.tgBot.callback.GetMemes;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class SearchNC implements INoCommand{
    private final UserChatStates userChatStates;
    private final SearchService service;
    private final GetMemes getGalleryMemes;
    @Override
    public UserState getNextState() {
        return UserState.GET_MEMES_SHOW;
    }

    @Override
    public UserState getState() {
        return UserState.FIND_MEME;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        sm.setText("Что ты хочешь найти?");
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId) {
        String req = update.getMessage().getText();
        var list = service.getFilesByQuery(req);

        var ids = list.stream().mapToInt(file -> file.id()).boxed().toList();
        getGalleryMemes.initById(tgId, ids);
        userChatStates.addUser(tgId, getNextState());
    }
}
