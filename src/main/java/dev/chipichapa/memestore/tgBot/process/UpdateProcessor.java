package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.exception.ResourceNotFoundException;
import dev.chipichapa.memestore.security.tg.TgAuthProvider;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
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
    private TgAuthProvider tgAuthProvider;

    public SendMessage process(Update update){
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();
        if (commandProcessor.isStartCommand(update)){
            return commandProcessor.process(update);
        }
        try {
            if (tgAuthProvider.authenticate(tgId)) {
                UserState status = userChatStates.getUserState(tgId);
                if (commandProcessor.isCommand(update)){
                    return commandProcessor.process(update);
                }

                if (UserState.isCallBackIgnore(status) && update.getCallbackQuery() != null) {
                    return callBackProcessor.process(update);
                }

                return noCommandProcessor.process(update);

            }
        } catch (ResourceNotFoundException ex){
            SendMessage sm = new SendMessage();
            long chatId = update.getCallbackQuery()==null?update.getMessage().getChatId()
                    :update.getCallbackQuery().getMessage().getChatId();
            sm.setChatId(chatId);

            sm.setText("Похоже я вас не знаю... Введите /start и мы познакомимся!");

            return sm;
        }



        return null;

    }

}
