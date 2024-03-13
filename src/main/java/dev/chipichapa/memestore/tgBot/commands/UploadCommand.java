package dev.chipichapa.memestore.tgBot.commands;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.domain.enumerated.AssetType;
import dev.chipichapa.memestore.dto.asset.AssetUploadRequest;
import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.tgBot.Bot;
import dev.chipichapa.memestore.tgBot.noCommands.asset.UploadMemeNC;
import dev.chipichapa.memestore.tgBot.req.TelegramMultipartFile;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@AllArgsConstructor
@Component("/upload")
public class UploadCommand implements Command{
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
