package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.commands.Command;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@AllArgsConstructor
public class CommandProcessor {
    private UserChatStates userChatStates;
    private List<Command> commandList;

    public SendMessage process(Update update){
        String input = update.getMessage().getText().split(" ")[0];
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        userChatStates.addUser(update.getMessage().getFrom().getId(), UserState.NO_ACTION);

        if (input.startsWith("/")) {
            Command command = commandList.stream().filter(c->c.getCommand().equals(input)).findFirst().orElse(null);
            if(command != null)
                return command.handleCommand(update, sm);
        }
        sm.setText("Простите, я вас не понимаю. Используйте /help для справки.");
        return sm;
    }

    public boolean isCommand(Update update){
        try{
            String input = update.getMessage().getText().split(" ")[0];
            return input.startsWith("/");

        } catch (Exception ex){
            return false;
        }
    }
}
