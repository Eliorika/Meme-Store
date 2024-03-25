package dev.chipichapa.memestore.tgBot.callback.meme;

import dev.chipichapa.memestore.tgBot.callback.ICallBack;
import dev.chipichapa.memestore.tgBot.noCommands.INoCommand;
import dev.chipichapa.memestore.tgBot.noCommands.SuccessfulStatusNC;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import dev.chipichapa.memestore.usecase.ifc.MemeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;

@Component
@AllArgsConstructor
public class MoveMeme implements ICallBack {
    private final Map<Long, Long> moveMeme = new HashMap<>();
    private final Map<Long, Long> moveFromGallery = new HashMap<>();
    private final MemeUseCase memeUseCase;
    private final UserChatStates userChatStates;
    @Override
    public SendMessage handle(Update update, SendMessage sm) {
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();
        String input = update.getCallbackQuery().getData();
        String result = input.replace(getCallBack(), "");
        memeUseCase.moveMeme(moveMeme.get(tgId), moveFromGallery.get(tgId), Long.valueOf(result));


        sm.setText("Мем был перенесен в другой альбом!");
        return sm;
    }

    @Override
    public String getCallBack() {
        return "!meme-gallery-move-";
    }


    public void addToMove(Long tgId, Long memeId, Long galleryId){
        moveMeme.put(tgId, memeId);
        moveFromGallery.put(tgId, galleryId);
    }

    private void clear(Long tgId){
        moveMeme.remove(tgId);
        moveFromGallery.remove(tgId);
    }
}
