package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.callback.CallBack;
import dev.chipichapa.memestore.tgBot.commands.Command;
import dev.chipichapa.memestore.tgBot.noCommands.NoCommand;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@AllArgsConstructor
public class NoCommandProcessor {
    private UserChatStates userChatStates;
    private List<NoCommand> noCommandList;

    public SendMessage process(Update update){
        SendMessage sm = new SendMessage();
        long chatId = update.getCallbackQuery()==null?update.getMessage().getChatId()
                :update.getCallbackQuery().getMessage().getChatId();
        sm.setChatId(chatId);
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();
        UserState status = userChatStates.getUserState(tgId);
        NoCommand noCommand = noCommandList.stream().filter(c->c.getState().equals(status)).findFirst().orElse(null);
            if(noCommand != null){
                noCommand.handleState(update, tgId);
                UserState newStatus = userChatStates.getUserState(tgId);
                if(!UserState.NO_ACTION.equals(newStatus)){
                    noCommand = noCommandList.stream().filter(c->c.getState().equals(newStatus)).findFirst().orElse(null);
                    if(noCommand != null){
                       return noCommand.handleMessage(update, sm);
                    }
                }
            }

        sm.setText("кусь");
        return sm;
    }
}