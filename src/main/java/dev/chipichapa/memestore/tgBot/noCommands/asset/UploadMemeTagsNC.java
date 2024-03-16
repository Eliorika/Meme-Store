package dev.chipichapa.memestore.tgBot.noCommands.asset;

import dev.chipichapa.memestore.domain.enumerated.AssetType;
import dev.chipichapa.memestore.dto.asset.AssetUploadRequest;
import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.tgBot.noCommands.NoCommand;
import dev.chipichapa.memestore.tgBot.noCommands.SuccessfulStatusNC;
import dev.chipichapa.memestore.tgBot.req.TelegramBotUtils;
import dev.chipichapa.memestore.tgBot.req.TelegramMultipartFile;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import dev.chipichapa.memestore.usecase.ifc.MemeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Component
public class UploadMemeTagsNC implements NoCommand {
    private final MemeUseCase memeUseCase;
    private UserChatStates userChatStates;
    private SuccessfulStatusNC successfulStatusNC;

    @Override
    public UserState getNextState() {
        return UserState.SUCCESS;
    }

    @Override
    public UserState getState() {
        return UserState.UPLOAD_MEME_TAGS;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        Long tgId = Long.valueOf(sm.getChatId());
        CreateMemeRequest req = userChatStates.getUserMeme(tgId);
        String answer = "Добавьте теги для мема " + req.getTitle() +
                "\n Уже имеющие теги:" + req.getTags().toString() +
                "\n Введите собственные теги в одну строку через пробел";
        sm.setText(answer);
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId) {
        List<String> tags = List.of(update.getMessage().getText().toString().split(" "));
        CreateMemeRequest req = userChatStates.getUserMeme(tgId);
        req.getTags().addAll(tags);
        req.getTags().stream().distinct().collect(Collectors.toList());
        try {
            memeUseCase.create(req);
            userChatStates.addUser(tgId, getNextState());
            successfulStatusNC.addMessage(tgId, "Мем " +req.getTitle()+" успешно добавлен");

        } catch (Exception e){
            //TODO FAILURE
        }

    }

}