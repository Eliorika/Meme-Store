package dev.chipichapa.memestore.tgBot;

import dev.chipichapa.memestore.tgBot.process.UpdateProcessor;
import dev.chipichapa.memestore.tgBot.req.TelegramBotUtils;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${telegram.bot-name}")
    private String botName;

    @Value("${telegram.bot-token}")
    private String token;

    @Value("${telegram.file.info-uri}")
    private String fileInfoUri;

    @Value("${telegram.file.storage-uri}")
    private String fileStorageUri;
    private final UpdateProcessor updateProcessor;
    private TelegramBotUtils telegramBotUtils;

    private static final Logger LOG = Logger.getLogger(Bot.class);

    public Bot(UpdateProcessor updateProcessor, TelegramBotUtils telegramBotUtils) {
        this.updateProcessor = updateProcessor;
        this.telegramBotUtils = telegramBotUtils;
    }


    @Override
    public void onUpdateReceived(Update update) {
        //if(update.hasMessage()&& update.getMessage().hasText()) {
            SendMessage sm = updateProcessor.process(update);
            try {
                execute(sm);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        //}

    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return this.token;
    }

    @EventListener({ContextRefreshedEvent.class})
    public void init() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);

    }

    @PostConstruct
    public void initMethods(){
        telegramBotUtils.registerBot(this);
    }

    public byte[] downloadMeme(String fileId){
        var restTemplate = new RestTemplate();
        var headers = new HttpHeaders();
        var request = new HttpEntity<>(headers);

        var response = restTemplate.exchange(
                fileInfoUri,
                HttpMethod.GET,
                request,
                String.class,
                token, fileId
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            var fullUri = fileStorageUri.replace("{token}", token)
                    .replace("{filePath}", getFilePath(response));
            URL urlObj = null;
            try {
                urlObj = new URL(fullUri);
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }

            //TODO подумать над оптимизацией
            try (InputStream is = urlObj.openStream()) {
                return is.readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    private String getFilePath(ResponseEntity<String> response) {
        var jsonObject = new JSONObject(response.getBody());
        return String.valueOf(jsonObject
                .getJSONObject("result")
                .getString("file_path"));
    }

}
