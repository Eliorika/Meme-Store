package dev.chipichapa.tgBot.process;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UpdateProcessor {
    public SendMessage process(Update update){
        if (update.getMessage() == null) {
            return null;
        }
        //TODO PROCESS
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        sm.setText("Простите, я вас не понимаю. Используйте /help для получения справки.");
        return sm;
    }
}
