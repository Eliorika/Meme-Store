package dev.chipichapa.memestore.tgBot.callback;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface CallBack {
    SendMessage handle(Update update, SendMessage sm);
}
