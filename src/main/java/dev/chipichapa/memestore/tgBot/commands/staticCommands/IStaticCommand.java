package dev.chipichapa.memestore.tgBot.commands.staticCommands;

import dev.chipichapa.memestore.tgBot.commands.ICommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface IStaticCommand extends ICommand {
    String getAbout();
}
