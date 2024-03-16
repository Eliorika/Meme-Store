package dev.chipichapa.memestore.tgBot.noCommands.gallery;

import dev.chipichapa.memestore.tgBot.noCommands.NoCommand;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class SuccessfulStatusNC implements NoCommand {
    private final UserChatStates userChatStates;
    @Override
    public final UserState getNextState() {
        return UserState.NO_ACTION;
    }

    @Override
    public final UserState getState() {
        return UserState.SUCCESS;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();
        handleState(update, tgId);
        sm.setText("Успешно!");
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId){
        userChatStates.addUser(tgId, getNextState());
    }
}
