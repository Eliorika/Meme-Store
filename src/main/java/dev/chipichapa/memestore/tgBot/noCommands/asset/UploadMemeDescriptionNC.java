package dev.chipichapa.memestore.tgBot.noCommands.asset;

import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.tgBot.noCommands.NoCommand;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.MemeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;


@AllArgsConstructor
@Component
public class UploadMemeDescriptionNC implements NoCommand {
    private UserChatStates userChatStates;

    @Override
    public UserState getNextState() {
        return UserState.UPLOAD_MEME_TAGS;
    }

    @Override
    public UserState getState() {
        return UserState.UPLOAD_MEME_DESCRIPTION;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        String answer = "Введите описание для вашего мема:";
        sm.setText(answer);
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId) {
        String description = update.getMessage().getText();
        CreateMemeRequest req = userChatStates.getUserMeme(tgId);
        req.setDescription(description);
        userChatStates.addUser(tgId, getNextState());
    }

}