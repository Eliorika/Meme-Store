package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.callback.CallBack;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class CallBackProcessor {
    private final ApplicationContext context;
    public SendMessage process(Update update){
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getCallbackQuery().getMessage().getChatId());
        String input = update.getCallbackQuery().getData();
        if (input.startsWith("!")) {
            try {
                CallBack callBack = (CallBack) context.getBean(input);
                return callBack.handle(update, sm);
            } catch (NoSuchBeanDefinitionException e){

            }

        }
        return sm;
    }
}
