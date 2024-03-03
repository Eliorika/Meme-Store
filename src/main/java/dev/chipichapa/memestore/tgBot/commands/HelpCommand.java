package dev.chipichapa.memestore.tgBot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@RequiredArgsConstructor
@Component("/help")
public class HelpCommand implements Command{

    private final List<Command> commands;

    @Override
    public String getCommand() {
        return "/help";
    }

    @Override
    public String getAbout() {
        return "справка о работе бота";
    }

    @Override
    public SendMessage handleCommand(Update update) {
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        StringBuilder answer = new StringBuilder();
        answer.append("Что я умею:\n");
        for (Command c : commands) {
            answer.append(c.getCommand());
            answer.append(" - ");
            answer.append(c.getAbout());
            answer.append("\n");
        }
        answer.append(this.getCommand());
        answer.append(" - ");
        answer.append(this.getAbout());
        answer.append("\n");
        sm.setText(answer.toString());
        return sm;
    }
}
