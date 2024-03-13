package dev.chipichapa.memestore.tgBot.noCommands.asset;

import dev.chipichapa.memestore.domain.enumerated.AssetType;
import dev.chipichapa.memestore.dto.asset.AssetUploadRequest;
import dev.chipichapa.memestore.tgBot.Bot;
import dev.chipichapa.memestore.tgBot.noCommands.NoCommand;
import dev.chipichapa.memestore.tgBot.req.TelegramBotMethods;
import dev.chipichapa.memestore.tgBot.req.TelegramMultipartFile;
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
import org.telegram.telegrambots.meta.generics.TelegramBot;

import java.io.IOException;
import java.io.InputStream;


@AllArgsConstructor
@Component
public class UploadMemeNC implements NoCommand {
    private final AssetsUseCase assetsUseCase;
    private TelegramBotMethods bot;



    @Override
    public UserState getNextState() {
        return UserState.NO_ACTION;
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
            assetsUseCase.upload(new AssetUploadRequest(AssetType.IMAGE, multipartFile));
        }


    }

    private MultipartFile convertToMultipartFile(PhotoSize photo) throws TelegramApiException, IOException {
        if (photo != null) {
            // Получаем идентификатор файла
            String fileId = photo.getFileId();

            // Получаем расширение файла
            String fileExtension = photo.getFileId().substring(fileId.lastIndexOf(".") + 1);

            // Получаем контент файла

                byte[] fileBytes = bot.downloadFile(fileId);

                return new TelegramMultipartFile(photo.getFileId(), fileBytes);

        }
        return null;

    }


}
