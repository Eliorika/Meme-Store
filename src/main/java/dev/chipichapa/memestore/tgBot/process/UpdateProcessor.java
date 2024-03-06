package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.commands.Command;
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


    private CommandProcessor commandProcessor;
    private CallBackProcessor callBackProcessor;

    public SendMessage process(Update update){
        if(update.getCallbackQuery()!=null){
            return callBackProcessor.process(update);
        }
        if (update.getMessage() == null) {
            return null;
        }
        if (update.getMessage().getText() !=null ){
            return commandProcessor.process(update);
        }

        return null;



    }

}
