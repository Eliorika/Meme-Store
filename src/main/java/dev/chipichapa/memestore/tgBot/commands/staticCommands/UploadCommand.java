package dev.chipichapa.memestore.tgBot.commands.staticCommands;

import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.tgBot.noCommands.asset.UploadMemeNC;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@AllArgsConstructor
@Component("/upload")
public class UploadCommand implements IStaticCommand {
    private UserChatStates userChatStates;
    private UploadMemeNC uploadMemeNC;

    @Override
    public String getCommand() {
        return "/upload";
    }

    @Override
    public String getAbout() {
        return "загрузить мем";
    }

    @Override
    public SendMessage handleCommand(Update update, SendMessage sm) {

        userChatStates.addUser(update.getMessage().getFrom().getId(), UserState.UPLOAD_MEME);
        userChatStates.addUserMeme(update.getMessage().getFrom().getId(), new CreateMemeRequest());
        return uploadMemeNC.handleMessage(update, sm);
    }

}
