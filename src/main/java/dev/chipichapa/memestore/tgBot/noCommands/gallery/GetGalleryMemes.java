package dev.chipichapa.memestore.tgBot.noCommands.gallery;

import dev.chipichapa.memestore.dto.meme.GetMemeResponse;
import dev.chipichapa.memestore.tgBot.noCommands.INoCommand;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class GetGalleryMemes implements INoCommand {

    private final AssetsUseCase assetsUseCase;
    private UserChatStates userChatStates;
    private final Map<Long, Integer> positions = new HashMap<>();
    private final Map<Long, List<GetMemeResponse>> memes = new HashMap<>();
    @Override
    public UserState getNextState() {
        return UserState.NO_ACTION;
    }

    @Override
    public UserState getState() {
        return UserState.GET_MEMES_SHOW;
    }

    @Override
    public SendMessage handleMessage(Update update, SendMessage sm) {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();

        Integer position = positions.get(tgId);
        List<GetMemeResponse> userMemes = memes.get(tgId);

        if(position >= userMemes.size()){
            sm.setText("Конец альбома!");
            userChatStates.addUser(tgId, getNextState());
            return sm;
        }

        List<InputMediaPhoto> media = new ArrayList<>();
        for (GetMemeResponse meme: userMemes) {
            var file = assetsUseCase.get(meme.getAssetId());
            byte[] file = files.get(i);
            String extension = extensions.get(i);
            InputMediaPhoto photo = new InputMediaPhoto().setMedia(file, "photo" + (i + 1) + "." + extension);
            media.add(photo);
        }


        sm.setText("");
        InlineKeyboardButton getAlbums = new InlineKeyboardButton();
        getAlbums.setText("Следующие 10");
        InlineKeyboardButton createAlbum = new InlineKeyboardButton();



        rowInline.add(getAlbums);
        rowInline.add(createAlbum);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }

    @Override
    public void handleState(Update update, Long tgId) {

    }
}
