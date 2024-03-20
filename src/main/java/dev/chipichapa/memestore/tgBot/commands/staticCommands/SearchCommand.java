package dev.chipichapa.memestore.tgBot.commands.staticCommands;

import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.service.ifc.SearchService;
import dev.chipichapa.memestore.tgBot.noCommands.SearchNC;
import dev.chipichapa.memestore.tgBot.noCommands.asset.UploadMemeNC;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class SearchCommand implements IStaticCommand{
    private UserChatStates userChatStates;
    private SearchNC searchNC;


    @Override
    public String getCommand() {
        return "/search";
    }

    @Override
    public SendMessage handleCommand(Update update, SendMessage sm) {
        userChatStates.addUser(update.getMessage().getFrom().getId(), UserState.FIND_MEME);

        return searchNC.handleMessage(update, sm);
    }

    @Override
    public String getAbout() {
        return "найти мем";
    }
}
