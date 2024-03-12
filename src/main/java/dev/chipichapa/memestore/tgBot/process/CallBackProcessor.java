package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.callback.CallBack;
import dev.chipichapa.memestore.tgBot.commands.Command;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@AllArgsConstructor
public class CallBackProcessor {
    private List<CallBack> callBackList;

    public SendMessage process(Update update){
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getCallbackQuery().getMessage().getChatId());
        String input = update.getCallbackQuery().getData();
        if (input.startsWith("!")) {
            CallBack callBack = callBackList.stream().filter(c->c.getCallBack().equals(input)).findFirst().orElse(null);
            if(callBack != null)
                return callBack.handle(update, sm);
        }
        return sm;
    }
}
