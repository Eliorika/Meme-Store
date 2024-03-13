package dev.chipichapa.memestore.tgBot.req;

import dev.chipichapa.memestore.tgBot.Bot;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.File;

import java.io.InputStream;

@Component
public class TelegramBotMethods {

    private Bot telegramBot;

    public byte[] downloadFile(String fileId){
        return telegramBot.downloadMeme(fileId);
    }

    public void registerBot(Bot bot){
        telegramBot = bot;
    }
}
