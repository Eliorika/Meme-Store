package dev.chipichapa.memestore.tgBot.noCommands.asset;

import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class UploadMemeGalleryNC implements INoCommand {
    private final MemeUseCase memeUseCase;
    private UserChatStates userChatStates;
    private GalleryUseCase galleryUseCase;
    private SuccessfulStatusNC successfulStatusNC;
    @Override
    public UserState getNextState() {
        return UserState.SUCCESS;
    }

    @Override
    public UserState getState() {
        return UserState.UPLOAD_MEME_GALLERY;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        var albums = galleryUseCase.getAll();

        sm.setText("Выберите альбом для сохранения:");
        for(Gallery gallery: albums){
            InlineKeyboardButton getAlbums = new InlineKeyboardButton();
            getAlbums.setText(gallery.getName());
            getAlbums.setCallbackData(String.valueOf(gallery.getId()));
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(getAlbums);
            rowsInline.add(rowInline);
        }


        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId){
        CreateMemeRequest req = userChatStates.getUserMeme(tgId);
        req.setGalleryId(Integer.valueOf(update.getCallbackQuery().getData()));

        try {
            memeUseCase.create(req);
            userChatStates.addUser(tgId, getNextState());
            successfulStatusNC.addMessage(tgId, "Мем " +req.getTitle()+" успешно добавлен!");

        } catch (Exception e){
            //TODO FAILURE
            userChatStates.addUser(tgId, UserState.NO_ACTION);

        }



    }
}
