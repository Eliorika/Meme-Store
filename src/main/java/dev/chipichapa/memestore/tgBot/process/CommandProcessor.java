package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.commands.Command;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class CommandProcessor {
    private ApplicationContext context;
    public SendMessage process(Update update){
        String input = update.getMessage().getText().split(" ")[0];
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());

        if (input.startsWith("/")) {
            try {
                Command command = (Command) context.getBean(input);
                return command.handleCommand(update, sm);
            } catch (NoSuchBeanDefinitionException e){

            }

        }


        sm.setText("Простите, я вас не понимаю. Используйте /help для получения справки.");
        return sm;
    }
}
