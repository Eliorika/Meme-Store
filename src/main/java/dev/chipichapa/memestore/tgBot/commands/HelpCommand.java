package ru.tinkoff.edu.java.bot.commands;

import lombok.RequiredArgsConstructor;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.tinkoff.edu.java.bot.UpdateProcessor;
import ru.tinkoff.edu.java.bot.client.ScrapperClient;


@RequiredArgsConstructor
public class HelpCommand implements Command{

    private final ScrapperClient scrapperClient;
    @Override
    public String command() {
        return "/help";
    }

    @Override
    public String about() {
        return "prints all commans";
    }

    @Override
    public SendMessage handleCommand(Update update) {
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        StringBuilder answer = new StringBuilder();
        answer.append("Commands I understand:\n");
        for (Command c : UpdateProcessor.getCommands()) {
            answer.append(c.command());
            answer.append(" - ");
            answer.append(c.about());
            answer.append("\n");
        }
        sm.setText(answer.toString());
        return sm;
    }
}
