package dev.chipichapa.memestore.tgBot.req;

import dev.chipichapa.memestore.tgBot.Bot;
import org.springframework.stereotype.Component;

@Component
public class TelegramBotUtils {

    private Bot telegramBot;

    public byte[] downloadFile(String fileId){
        return telegramBot.downloadMeme(fileId);
    }

    public void registerBot(Bot bot){
        telegramBot = bot;
    }
}
