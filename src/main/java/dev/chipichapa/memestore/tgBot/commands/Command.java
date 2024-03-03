package dev.chipichapa.memestore.tgBot;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    String command();
    String about();
    SendMessage handleCommand(Update update);
    default boolean supports(Update update){
        return command().equals(update.getMessage().getText());
    }
}
