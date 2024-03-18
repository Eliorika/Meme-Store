package dev.chipichapa.memestore.tgBot.callback;

import dev.chipichapa.memestore.dto.meme.GetMemeResponse;
import dev.chipichapa.memestore.tgBot.commands.dynamicCommands.IDynamicCommands;
import dev.chipichapa.memestore.tgBot.noCommands.INoCommand;
import dev.chipichapa.memestore.tgBot.req.TelegramBotUtils;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import dev.chipichapa.memestore.usecase.ifc.MemeUseCase;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientAutoConfiguration;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Component
@AllArgsConstructor
public class GetGalleryMemes implements CallBack {

    private final AssetsUseCase assetsUseCase;
    private final MemeUseCase memeUseCase;
    private UserChatStates userChatStates;
    private final TelegramBotUtils bot;
    private final Map<Long, Integer> positions = new HashMap<>();
    private final Map<Long, Set<GetMemeResponse>> memes = new HashMap<>();


    @Override
    public SendMessage handle(Update update, SendMessage sm) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();
        userChatStates.addUser(tgId, UserState.GET_MEMES_SHOW);
        Integer position = positions.get(tgId);

        Set<GetMemeResponse> userMemes = memes.get(tgId);

        List<InputMedia> media = new ArrayList<>();
        for (GetMemeResponse meme: userMemes) {
            var assetGetResponse = assetsUseCase.getById(meme.getAssetId());
            byte[] file = assetGetResponse.file();
            String extension = assetGetResponse.extension();
            InputStream photoStream = new ByteArrayInputStream(file);
            InputMediaPhoto photo = new InputMediaPhoto();
            photo.setMedia(photoStream, meme.hashCode() + "." + extension);
            media.add(photo);
        }

        SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(tgId);
        sendMediaGroup.setMedias(media);

        if(!bot.sendMedia(sendMediaGroup)){
            sm.setText("Что-то пошло не так");
            memes.remove(tgId);
            positions.remove(tgId);
            userChatStates.addUser(tgId, UserState.NO_ACTION);
            return sm;
        }
        var newPos = position+10;
        if(newPos+10 >= userMemes.size()){
            sm.setText("Конец альбома!");
            memes.remove(tgId);
            positions.remove(tgId);
            userChatStates.addUser(tgId, UserState.NO_ACTION);
            return sm;
        }

        sm.setText("");
        InlineKeyboardButton forward = new InlineKeyboardButton();
        forward.setText("Следующие 10");
        forward.setCallbackData(getCallBack());
        InlineKeyboardButton createAlbum = new InlineKeyboardButton();


        rowInline.add(forward);
        rowInline.add(createAlbum);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }

    @Override
    public String getCallBack() {
        return "!next10";
    }

    public void init(Long id, int galleryId){
        var userMemes = memeUseCase.getMemesFromGallery(galleryId);
        memes.put(id, userMemes);
        positions.put(id, 0);

    }

}
