package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.commands.Command;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class UpdateProcessor {

    private UserChatStates userChatStates;
    private CommandProcessor commandProcessor;
    private CallBackProcessor callBackProcessor;
    private NoCommandProcessor noCommandProcessor;

    public SendMessage process(Update update){
        if (commandProcessor.isCommand(update)){
            return commandProcessor.process(update);
        }
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();
        UserState status = userChatStates.getUserState(tgId);

        if(UserState.NO_ACTION.equals(status) && update.getCallbackQuery()!=null){
            return callBackProcessor.process(update);
        }

        return noCommandProcessor.process(update);



        //return null;

    }

}
