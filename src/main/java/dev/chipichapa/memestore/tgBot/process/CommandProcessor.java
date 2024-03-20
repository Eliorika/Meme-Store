package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.commands.ICommand;
import dev.chipichapa.memestore.tgBot.commands.dynamicCommands.IDynamicCommands;
import dev.chipichapa.memestore.tgBot.commands.staticCommands.IStaticCommand;
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
    private List<IStaticCommand> staticCommands;
    private List<IDynamicCommands> dynamicCommands;

    public SendMessage process(Update update){
        String input = update.getMessage().getText().split(" ")[0];
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getMessage().getChatId());
        userChatStates.addUser(update.getMessage().getFrom().getId(), UserState.NO_ACTION);

        if (input.startsWith("/")) {
            ICommand command = staticCommands.stream().filter(c->c.getCommand().equals(input)).findFirst().orElse(null);
            if(command != null)
                return command.handleCommand(update, sm);
            command = dynamicCommands.stream().filter(c->input.contains(c.getCommand())).findFirst().orElse(null);
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

    public boolean isStartCommand(Update update){
        try{
            String input = update.getMessage().getText().split(" ")[0];
            return input.equals("/start");

        } catch (Exception ex){
            return false;
        }
    }
}
