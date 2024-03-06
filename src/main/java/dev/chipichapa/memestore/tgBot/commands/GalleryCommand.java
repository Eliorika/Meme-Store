package dev.chipichapa.memestore.tgBot.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Component("/gallery")
public class GalleryCommand implements Command{

    @Override
    public String getCommand() {
        return "/gallery";
    }

    @Override
    public String getAbout() {
        return "твои альбомы";
    }

    @Override
    public SendMessage handleCommand(Update update, SendMessage sm) {

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        InlineKeyboardButton getAlbums = new InlineKeyboardButton();
        getAlbums.setText("Посмотреть альбомы");
        getAlbums.setCallbackData("!gallery-get-all");

        InlineKeyboardButton createAlbum = new InlineKeyboardButton();
        createAlbum.setText("Создать альбом");
        createAlbum.setCallbackData("!gallery-create-album");

        rowInline.add(getAlbums);
        rowInline.add(createAlbum);
        rowsInline.add(rowInline);

        markupInline.setKeyboard(rowsInline);
        sm.setReplyMarkup(markupInline);

        sm.setText("Что вы хотели сделать?");
        return sm;
    }
}
