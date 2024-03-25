package dev.chipichapa.memestore.tgBot.callback.gallery;

import dev.chipichapa.memestore.tgBot.callback.ICallBack;
import dev.chipichapa.memestore.tgBot.noCommands.confirm.GalleyDeletionConfirmNc;
import dev.chipichapa.memestore.tgBot.noCommands.confirm.MemeDeletionConfirmNc;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class DeleteGallery implements ICallBack {
    private UserChatStates userChatStates;
    private GalleyDeletionConfirmNc confirmNc;
    @Override
    public SendMessage handle(Update update, SendMessage sm) {
        userChatStates.addUser(update.getCallbackQuery().getFrom().getId(), UserState.DELETE_GALLERY);
        String input = update.getCallbackQuery().getData().replace(getCallBack(), "");
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();
        confirmNc.addToDelete(tgId, Long.valueOf(input));
        return confirmNc.handleMessage(update, sm);
    }

    @Override
    public String getCallBack() {
        return "!gallery-delete-";
    }
}
