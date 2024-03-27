package dev.chipichapa.memestore.tgBot.noCommands.asset;

import dev.chipichapa.memestore.domain.enumerated.AssetType;
import dev.chipichapa.memestore.dto.asset.AssetUploadRequest;
import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.tgBot.noCommands.INoCommand;
import dev.chipichapa.memestore.tgBot.req.TelegramBotUtils;
import dev.chipichapa.memestore.tgBot.req.MemeMultipartFile;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;


@AllArgsConstructor
@Component
public class UploadMemeNC implements INoCommand {
    private final AssetsUseCase assetsUseCase;
    private TelegramBotUtils bot;
    private UserChatStates userChatStates;

    @Override
    public UserState getNextState() {
        return UserState.UPLOAD_MEME_TITLE;
    }

    @Override
    public UserState getState() {
        return UserState.UPLOAD_MEME;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        sm.setText("Загрузите мем:");
        return sm;
    }

    @Override
    public void handleState(Update update,  Long tgId) {

        PhotoSize photo = update.getMessage().getPhoto().stream()
                .sorted((p1, p2) -> Integer.compare(p2.getWidth(), p1.getWidth()))
                .findFirst()
                .orElse(null);

        MultipartFile multipartFile;
        try {
            multipartFile = convertToMultipartFile(photo);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(multipartFile != null){
            CreateMemeRequest req = userChatStates.getUserMeme(tgId);
            var resp = assetsUseCase.upload(new AssetUploadRequest(AssetType.IMAGE, multipartFile));
            req.setAssetTicket(resp.temporaryTicket());
            req.setTags(assetsUseCase.getSuggestTags(resp.temporaryTicket()).tags().stream().toList());
        }
        userChatStates.addUser(tgId, getNextState());
    }

    private MultipartFile convertToMultipartFile(PhotoSize photo) throws TelegramApiException, IOException {
        if (photo != null) {
            // Получаем идентификатор файла
            String fileId = photo.getFileId();

            // Получаем расширение файла
            String fileExtension = photo.getFileId().substring(fileId.lastIndexOf(".") + 1);

            // Получаем контент файла

                byte[] fileBytes = bot.downloadFile(fileId);

                return new MemeMultipartFile(photo.getFileId(), fileBytes);

        }
        return null;

    }


}
