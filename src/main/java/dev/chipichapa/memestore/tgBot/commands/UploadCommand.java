package dev.chipichapa.memestore.tgBot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;


@RequiredArgsConstructor
@Component("/upload")
public class UploadCommand implements Command{

    @Override
    public String getCommand() {
        return "/upload";
    }

    @Override
    public String getAbout() {
        return "загрузить мем";
    }

    @Override
    public SendMessage handleCommand(Update update, SendMessage sm) {
        if(update.getMessage().getPhoto() == null){
            sm.setText("Простите, я не получил фото. Давайте попробуем еще раз!");
            return sm;
        }

        sm.setText("Туть");
        return sm;
    }
}
