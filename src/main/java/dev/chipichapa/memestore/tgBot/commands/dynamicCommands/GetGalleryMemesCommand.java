package dev.chipichapa.memestore.tgBot.commands.dynamicCommands;

import dev.chipichapa.memestore.tgBot.callback.GetMemes;
import jakarta.ws.rs.NotAllowedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@AllArgsConstructor
public class GetGalleryMemesCommand implements IDynamicCommands{
    private final GetMemes getGalleryMemes;

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


        try {
            getGalleryMemes.initByGallery(tgId, Integer.valueOf(result));
        } catch (NotAllowedException e){
            sm.setText("Не ходи, зашибут!");
            return sm;
        }

        return getGalleryMemes.handle(update, sm);
    }


}
