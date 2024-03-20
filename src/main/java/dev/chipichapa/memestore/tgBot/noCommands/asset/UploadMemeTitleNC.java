package dev.chipichapa.memestore.tgBot.noCommands.asset;

import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.tgBot.noCommands.INoCommand;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@AllArgsConstructor
@Component
public class UploadMemeTitleNC implements INoCommand {
    private UserChatStates userChatStates;

    @Override
    public UserState getNextState() {
        return UserState.UPLOAD_MEME_DESCRIPTION;
    }

    @Override
    public UserState getState() {
        return UserState.UPLOAD_MEME_TITLE;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        String answer = "Введите название для вашего мема:";
        sm.setText(answer);
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId) {
        String title = update.getMessage().getText();
        CreateMemeRequest req = userChatStates.getUserMeme(tgId);
        req.setTitle(title);
        userChatStates.addUser(tgId, getNextState());
    }

}