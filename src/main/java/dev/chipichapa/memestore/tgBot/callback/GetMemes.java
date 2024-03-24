package dev.chipichapa.memestore.tgBot.callback;

import dev.chipichapa.memestore.dto.meme.GetMemeResponse;
import dev.chipichapa.memestore.tgBot.req.TelegramBotUtils;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import dev.chipichapa.memestore.usecase.ifc.AssetsUseCase;
import dev.chipichapa.memestore.usecase.ifc.MemeUseCase;
import jakarta.ws.rs.NotAllowedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.*;

@Component
@AllArgsConstructor
public class GetMemes implements CallBack {

    private final AssetsUseCase assetsUseCase;
    private final MemeUseCase memeUseCase;
    private UserChatStates userChatStates;
    private final TelegramBotUtils bot;
    private final Map<Long, Integer> positions = new HashMap<>();
    private final Map<Long, Set<GetMemeResponse>> memes = new HashMap<>();
    private final Set<Long> isSearch = new HashSet<>();
    private final int imgCountPerMessage = 5;


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
        if(userMemes == null || userMemes.isEmpty()){
            sm.setText("Нам нечего показать!");
            clear(tgId);
            userChatStates.addUser(tgId, UserState.NO_ACTION);
            return sm;
        }

        List<SendPhoto> media = new ArrayList<>();
        int i = 0;
        Iterator<GetMemeResponse> iterator = userMemes.iterator();
        while (iterator.hasNext()) {
            GetMemeResponse meme = iterator.next();
            var assetGetResponse = assetsUseCase.getById(meme.getAssetId());
            byte[] file = assetGetResponse.file();
            String extension = assetGetResponse.extension();
            InputStream photoStream = new ByteArrayInputStream(file);
            InputFile photo = new InputFile(photoStream, meme.hashCode() + "." + extension);
            SendPhoto sendPhoto = new SendPhoto();
            sendPhoto.setPhoto(photo);
            sendPhoto.setCaption("Название: " + meme.getTitle() + "\n\nОписание: " + meme.getDescription());
            sendPhoto.setChatId(tgId);
            if(!isSearch.contains(tgId)){
                sendPhoto.setReplyMarkup(createEditionKeyboard());
            }

            media.add(sendPhoto);
            iterator.remove();
            i++;
            if(i>=imgCountPerMessage)
                break;
        }


        if(!bot.sendMedia(media)){
            sm.setText("Что-то пошло не так");

            userChatStates.addUser(tgId, UserState.NO_ACTION);
            return sm;
        }

        var newPos = position+i;
        if(newPos >= userMemes.size()){
            sm.setText("Конец альбома!");
            clear(tgId);
            userChatStates.addUser(tgId, UserState.NO_ACTION);

            return sm;
        }

        sm.setText("Смотрим дальше?");
        InlineKeyboardButton forward = new InlineKeyboardButton();
        forward.setText("Следующие " + imgCountPerMessage);
        forward.setCallbackData(getCallBack());
        positions.put(tgId, newPos);

        rowInline.add(forward);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);
        return sm;
    }

    @Override
    public String getCallBack() {
        return "!nextMemes";
    }

    public void initByGallery(Long id, int galleryId, boolean canEdit){
        var userMemes = memeUseCase.getMemesFromGallery(galleryId);
        if(userMemes == null)
            throw new NotAllowedException("Не ходи, зашибут!");
        memes.put(id, userMemes);
        positions.put(id, 0);

        if(canEdit)
            isSearch.remove(id);
        else isSearch.add(id);
    }

    public void initById(Long id, List<Integer> memesId, boolean isSearching){
        Set<GetMemeResponse> list = new HashSet<>();
        for(Integer memeId: memesId){
            list.add(memeUseCase.getById(memeId));
        }
        memes.put(id, list);
        positions.put(id, 0);
        if(isSearching)
            isSearch.add(id);
        else
            isSearch.remove(id);

    }

    private void clear(Long tgId){
        memes.remove(tgId);
        positions.remove(tgId);
        isSearch.remove(tgId);
    }

    private InlineKeyboardMarkup createEditionKeyboard(long memeId){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton edit = new InlineKeyboardButton();
        edit.setText("Редактировать мем");
        edit.setCallbackData("!meme-edit-" + memeId);

        InlineKeyboardButton delete = new InlineKeyboardButton();
        delete.setText("Удалить мем");
        delete.setCallbackData("!meme-delete" + memeId);

        InlineKeyboardButton move = new InlineKeyboardButton();
        move.setText("Переместить мем");
        move.setCallbackData("!meme-move" + memeId);

        rowInline.add(edit);
        rowInline.add(delete);
        rowInline.add(move);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

}
