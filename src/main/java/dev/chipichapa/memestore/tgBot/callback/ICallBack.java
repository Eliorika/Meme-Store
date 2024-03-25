package dev.chipichapa.memestore.tgBot.callback;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface ICallBack {
    SendMessage handle(Update update, SendMessage sm);
    String getCallBack();
}
