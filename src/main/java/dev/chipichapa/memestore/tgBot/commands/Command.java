package dev.chipichapa.memestore.tgBot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    String getCommand();
    String getAbout();
    SendMessage handleCommand(Update update);
    default boolean supports(Update update){
        return getCommand().equals(update.getMessage().getText());
    }
}
