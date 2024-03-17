package dev.chipichapa.memestore.tgBot.noCommands;

import dev.chipichapa.memestore.tgBot.states.UserState;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface INoCommand {
    UserState getNextState();
    UserState getState();
    SendMessage handleMessage(Update update, SendMessage sm);

    void handleState(Update update, Long tgId);
}
