package dev.chipichapa.memestore.tgBot.callback.gallery;

import dev.chipichapa.memestore.domain.entity.user.User;
import dev.chipichapa.memestore.domain.model.Gallery;
import dev.chipichapa.memestore.tgBot.callback.CallBack;
import dev.chipichapa.memestore.usecase.ifc.GalleryUseCase;
import dev.chipichapa.memestore.usecase.ifc.GetUserUseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component("!gallery-get-all")
@AllArgsConstructor
public class GetGalleries implements CallBack {
    private final GalleryUseCase galleryUseCase;
    private final GetUserUseCase getUserUseCase;

    @Override
    public SendMessage handle(Update update, SendMessage sm) {
        User user = getUserUseCase.getByTg(update.getCallbackQuery().getFrom().getId());

        var ls = galleryUseCase.getAllForUser(user);

        StringBuilder answer = new StringBuilder();

        for (Gallery gallery:ls) {
            answer.append(galleryMessage(gallery));
        }
        sm.setText(answer.toString());
        return sm;
    }

    @Override
    public String getCallBack() {
        return "!gallery-get-all";
    }

    private String galleryMessage(Gallery gallery){
        StringBuilder answer = new StringBuilder();
        answer.append("Альбом \"" + gallery.getName() +"\"\n");
        if(gallery.getDescription() != null)
           answer.append(gallery.getDescription() + "\n");

        answer.append("Посмотреть мемы: /downloadGallery" + gallery.getId() + "\n\n");

        return answer.toString();
    }




}
