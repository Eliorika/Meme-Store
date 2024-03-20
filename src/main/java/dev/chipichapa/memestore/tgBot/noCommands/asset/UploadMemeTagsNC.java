package dev.chipichapa.memestore.tgBot.noCommands.asset;

import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
import dev.chipichapa.memestore.tgBot.noCommands.INoCommand;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Component
public class UploadMemeTagsNC implements INoCommand {

    private UserChatStates userChatStates;

    @Override
    public UserState getNextState() {
        return UserState.UPLOAD_MEME_GALLERY;
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
                "\n Уже имеющие теги: " + req.getTags().toString() +
                "\n Введите собственные теги в одну строку через пробел";
        sm.setText(answer);
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId) {
        List<String> tags = new ArrayList<>(Arrays.asList(update.getMessage().getText().split("\\s+")));
        CreateMemeRequest req = userChatStates.getUserMeme(tgId);
        List<String> currentTags =  new ArrayList<>(req.getTags());
        currentTags.addAll(tags);
        List<String> distinctTags = currentTags.stream().distinct().collect(Collectors.toList());
        req.setTags(distinctTags);
        userChatStates.addUser(tgId, getNextState());
    }

}