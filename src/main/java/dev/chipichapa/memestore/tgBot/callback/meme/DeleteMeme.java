package dev.chipichapa.memestore.tgBot.callback.meme;

import dev.chipichapa.memestore.tgBot.callback.ICallBack;
import dev.chipichapa.memestore.tgBot.noCommands.confirm.MemeDeletionConfirmNc;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class DeleteMeme implements ICallBack {
    private UserChatStates userChatStates;
    private MemeDeletionConfirmNc confirmNc;
    @Override
    public SendMessage handle(Update update, SendMessage sm) {
        userChatStates.addUser(update.getCallbackQuery().getFrom().getId(), UserState.DELETE_MEME);
        String input = update.getCallbackQuery().getData();
        String[] result = input.replace(getCallBack(), "").split("-");
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();
        confirmNc.addToDelete(tgId, Long.valueOf(result[0]), Long.valueOf(result[1]));
        return confirmNc.handleMessage(update, sm);
    }

    @Override
    public String getCallBack() {
        return "!meme-delete-";
    }
}