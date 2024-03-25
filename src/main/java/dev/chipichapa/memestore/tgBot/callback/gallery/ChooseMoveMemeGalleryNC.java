package dev.chipichapa.memestore.tgBot.callback.gallery;

import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.tgBot.callback.ICallBack;
import dev.chipichapa.memestore.tgBot.callback.meme.MoveMeme;
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
public class ChooseMoveMemeGalleryNC implements ICallBack {
    private final MemeUseCase memeUseCase;
    private UserChatStates userChatStates;
    private GalleryUseCase galleryUseCase;
    private MoveMeme moveMeme;

    @Override
    public SendMessage handle(Update update, SendMessage sm) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        userChatStates.addUser(update.getCallbackQuery().getFrom().getId(), UserState.MOVE_MEME);
        String input = update.getCallbackQuery().getData();
        String[] result = input.replace(getCallBack(), "").split("-");
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();

        var albums = galleryUseCase.getAll().stream().filter(al->(al.getId()!=Long.valueOf(result[1]))).toList();
        moveMeme.addToMove(tgId, Long.valueOf(result[0]), Long.valueOf(result[1]));

        sm.setText("Куда переместить мем:");
        for(Gallery gallery: albums){
            InlineKeyboardButton getAlbums = new InlineKeyboardButton();
            getAlbums.setText(gallery.getName());
            getAlbums.setCallbackData("!meme-gallery-move-"+ gallery.getId());
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add(getAlbums);
            rowsInline.add(rowInline);
        }

        //userChatStates.addUser(tgId, UserState.DELETE_GALLERY);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }

    @Override
    public String getCallBack() {
        return "!meme-move-";
    }


}
