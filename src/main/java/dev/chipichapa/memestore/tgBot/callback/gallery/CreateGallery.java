package dev.chipichapa.memestore.tgBot.callback.gallery;

import dev.chipichapa.memestore.domain.entity.Album;
import dev.chipichapa.memestore.tgBot.callback.CallBack;
import dev.chipichapa.memestore.tgBot.noCommands.gallery.create.CreateGalleryNameNC;
import dev.chipichapa.memestore.tgBot.states.UserChatStates;
import dev.chipichapa.memestore.tgBot.states.UserState;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class CreateGallery implements CallBack {
    private UserChatStates userChatStates;
    private CreateGalleryNameNC createGalleryNameNC;
    @Override
    public SendMessage handle(Update update, SendMessage sm) {
        userChatStates.addUser(update.getCallbackQuery().getFrom().getId(), UserState.CREATING_ALBUM_NAME);
        userChatStates.addUserAlbum(update.getCallbackQuery().getFrom().getId(), new Album());

        return createGalleryNameNC.handleMessage(update, sm);
    }

    @Override
    public String getCallBack() {
        return "!gallery-create-album";
    }
}
