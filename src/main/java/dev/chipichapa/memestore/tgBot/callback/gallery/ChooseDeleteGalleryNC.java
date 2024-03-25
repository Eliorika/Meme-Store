package dev.chipichapa.memestore.tgBot.callback.gallery;

import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.dto.meme.CreateMemeRequest;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class ChooseDeleteGalleryNC implements ICallBack {
    private final MemeUseCase memeUseCase;
    private UserChatStates userChatStates;
    private GalleryUseCase galleryUseCase;
    private SuccessfulStatusNC successfulStatusNC;

    @Override
    public SendMessage handle(Update update, SendMessage sm) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        var albums = galleryUseCase.getAll();

        sm.setText("Выберите альбом для удаления:");
        for(Gallery gallery: albums){
            InlineKeyboardButton getAlbums = new InlineKeyboardButton();
            getAlbums.setText(gallery.getName());
            getAlbums.setCallbackData("!gallery-delete-"+ gallery.getId());
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(getAlbums);
            rowsInline.add(rowInline);
        }
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();

        userChatStates.addUser(tgId, UserState.DELETE_GALLERY);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }

    @Override
    public String getCallBack() {
        return "!deleteAlbum";
    }


}
