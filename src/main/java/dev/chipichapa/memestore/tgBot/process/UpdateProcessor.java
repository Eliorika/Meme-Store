package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.commands.Command;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class UpdateProcessor {

    @Autowired
    private ApplicationContext context;

    public SendMessage process(Update update){
        if (update.getMessage() == null) {
            return null;
        }

        String input = update.getMessage().getText().split(" ")[0];

        if (input.startsWith("/")) {
            try {
                Command command = (Command) context.getBean(input);
                return command.handleCommand(update);
            } catch (NoSuchBeanDefinitionException e){

            }

        }

        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        sm.setText("Простите, я вас не понимаю. Используйте /help для получения справки.");
        return sm;
    }
}
