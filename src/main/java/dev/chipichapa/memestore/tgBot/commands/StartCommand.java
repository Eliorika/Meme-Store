package ru.tinkoff.edu.java.bot.commands;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;

@RequiredArgsConstructor
public class StartCommand implements Command{
    private final ScrapperClient scrapperClient;
    @Override
    public String command() {
        return "/start";
    }

    @Override
    public String about() {
        return "registers new user";
    }

    @Override
    public SendMessage handleCommand(Update update) {
        SendMessage sm = new SendMessage();
        //String msText = update.getMessage().getText();
        sm.setChatId(update.getMessage().getChatId());
        String answer = "Hi, " + update.getMessage().getChat().getFirstName() +
            "! What's up! \nEnter /help to find out what I can!";
        sm.setText(answer);
        scrapperClient.registerChat(update.getMessage().getChat().getId());
        return sm;
    }


}
