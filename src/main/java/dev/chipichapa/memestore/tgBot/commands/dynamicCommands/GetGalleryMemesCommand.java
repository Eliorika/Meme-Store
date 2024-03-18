package dev.chipichapa.memestore.tgBot.commands.dynamicCommands;

import dev.chipichapa.memestore.tgBot.callback.GetGalleryMemes;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class GetGalleryMemesCommand implements IDynamicCommands{
    private final GetGalleryMemes getGalleryMemes;

    @Override
    public String getCommand() {
        return "/downloadGallery";
    }

    @Override
    public SendMessage handleCommand(Update update, SendMessage sm) {
        String input = update.getMessage().getText().split(" ")[0];
        String result = input.replace(getCommand(), "");
        long tgId = update.getCallbackQuery()==null?update.getMessage().getFrom().getId()
                :update.getCallbackQuery().getFrom().getId();


        getGalleryMemes.init(tgId, Integer.valueOf(result));
        return getGalleryMemes.handle(update, sm);
    }


}
