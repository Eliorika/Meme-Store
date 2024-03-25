package dev.chipichapa.memestore.tgBot.noCommands;

import dev.chipichapa.memestore.tgBot.callback.meme.GetMemes;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class RouterGetMemesNC implements INoCommand{
    private final UserChatStates userChatStates;
    private final GetMemes getGalleryMemes;
    @Override
    public UserState getNextState() {
        return UserState.GET_MEMES_SHOW;
    }

    @Override
    public UserState getState() {
        return UserState.GET_MEMES_SHOW;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        return getGalleryMemes.handle(update, sm);
    }

    @Override
    public void handleState(Update update, Long tgId) {
        userChatStates.addUser(tgId, getNextState());
    }
}
