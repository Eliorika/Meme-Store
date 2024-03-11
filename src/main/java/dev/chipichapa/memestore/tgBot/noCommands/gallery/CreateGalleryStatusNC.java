package dev.chipichapa.memestore.tgBot.noCommands.gallery;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.dto.gallery.GalleryCreateRequest;
import dev.chipichapa.memestore.tgBot.noCommands.NoCommand;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
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
public class CreateGalleryStatusNC implements NoCommand {
    private UserChatStates userChatStates;
    private GalleryUseCase galleryUseCase;
    @Override
    public UserState getNextState() {
        return UserState.SUCCESS;
    }

    @Override
    public UserState getState() {
        return UserState.CREATING_ALBUM_STATUS;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        sm.setText("Выберите статус");
        InlineKeyboardButton getAlbums = new InlineKeyboardButton();
        getAlbums.setText("Приватный");
        getAlbums.setCallbackData("!gallery-create-status-private");

        InlineKeyboardButton createAlbum = new InlineKeyboardButton();
        createAlbum.setText("Публичный");
        createAlbum.setCallbackData("!gallery-create-status-public");

        rowInline.add(getAlbums);
        rowInline.add(createAlbum);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId){
        Album album = userChatStates.getUserAlbum(tgId);
        album.setStatus(update.getCallbackQuery().getData().equals("!gallery-create-status-public"));

        userChatStates.addUser(tgId, getNextState());
        galleryUseCase.create(new GalleryCreateRequest(album.getName(), album.getDescription(), album.getStatus()));
        //userChatStates.addUserAlbum();
    }
}