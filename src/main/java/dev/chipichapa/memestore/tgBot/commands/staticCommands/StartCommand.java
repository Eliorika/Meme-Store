package dev.chipichapa.memestore.tgBot.commands.staticCommands;

import dev.chipichapa.memestore.dto.auth.TgRegisterRequest;
import dev.chipichapa.memestore.service.ifc.AuthService;
import dev.chipichapa.memestore.usecase.ifc.UserRegisterUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

@RequiredArgsConstructor
@Component("/start")
public class StartCommand implements IStaticCommand {

    private final UserRegisterUseCase userRegisterUseCase;
    private final AuthService authService;

    @Override
    public String getCommand() {
        return "/start";
    }

    @Override
    public String getAbout() {
        return "начать работу";
    }

    @Override
    public SendMessage handleCommand(Update update, SendMessage sm) {
        User user = update.getMessage().getFrom();
        TgRegisterRequest tgRegisterRequest = new TgRegisterRequest(user.getUserName(),
                user.getFirstName()+" "+user.getLastName(),
                user.getId());
        userRegisterUseCase.registerTg(tgRegisterRequest);
        String answer = "Привет, " + update.getMessage().getChat().getFirstName() +
            "! Как дела? \nВведи /help, чтобы узнать, что я умею!";
        sm.setText(answer);
        return sm;
    }


}
