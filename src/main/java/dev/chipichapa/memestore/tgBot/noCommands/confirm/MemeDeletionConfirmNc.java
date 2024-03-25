package dev.chipichapa.memestore.tgBot.noCommands.confirm;

import dev.chipichapa.memestore.tgBot.noCommands.INoCommand;
import dev.chipichapa.memestore.tgBot.noCommands.SuccessfulStatusNC;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.MemeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class MemeDeletionConfirmNc implements INoCommand {
    private final Map<Long, Long> deletionMeme = new HashMap<>();
    private final Map<Long, Long> deletionGallery = new HashMap<>();
    private final MemeUseCase memeUseCase;
    private final UserChatStates userChatStates;
    private final SuccessfulStatusNC successfulStatusNC;

    @Override
    public UserState getNextState() {
        return UserState.SUCCESS;
    }

    @Override
    public UserState getState() {
        return UserState.DELETE_MEME;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        sm.setText("Введи 'yes' если уверен, что хочешь удалить!");
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId) {
        String answer = update.getMessage().getText();
        try {
            if("yes".equals(answer)){
                memeUseCase.deleteMeme(deletionMeme.get(tgId), deletionGallery.get(tgId));
                clear(tgId);
                userChatStates.addUser(tgId, getNextState());
                successfulStatusNC.addMessage(tgId, "Удалено успешно!");
            }

        } catch (Exception e){
            System.out.println("==========error========");
            e.printStackTrace();
        }

    }

    public void addToDelete(Long tgId, Long memeId, Long galleryId){
        deletionMeme.put(tgId, memeId);
        deletionGallery.put(tgId, galleryId);
    }

    private void clear(Long tgId){
        deletionMeme.remove(tgId);
        deletionGallery.remove(tgId);
    }
}
