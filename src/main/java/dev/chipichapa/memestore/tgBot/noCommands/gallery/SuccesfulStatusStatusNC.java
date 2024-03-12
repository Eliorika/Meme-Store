package dev.chipichapa.memestore.tgBot.noCommands.gallery;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.tgBot.noCommands.NoCommand;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class SuccesfulStatusStatusNC implements NoCommand {
    private UserChatStates userChatStates;
    @Override
    public UserState getNextState() {
        return UserState.NO_ACTION;
    }

    @Override
    public UserState getState() {
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
