package dev.chipichapa.memestore.tgBot.req;

import dev.chipichapa.memestore.tgBot.Bot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramBotUtils {

    private Bot telegramBot;

    public byte[] downloadFile(String fileId){
        return telegramBot.downloadMeme(fileId);
    }

    public void registerBot(Bot bot){
        telegramBot = bot;
    }

    public boolean sendMedia(List<SendPhoto> photos) {
        try {
            for (SendPhoto ph: photos) {
                telegramBot.execute(ph);
            }
            return true;
        } catch (TelegramApiException e) {
            return false;
        }
    }
}
