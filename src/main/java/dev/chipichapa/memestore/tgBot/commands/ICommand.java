package dev.chipichapa.memestore.tgBot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ICommand {
    String getCommand();

    SendMessage handleCommand(Update update, SendMessage sm);
    default boolean supports(Update update){
        return getCommand().equals(update.getMessage().getText());
    }
}
