package dev.chipichapa.memestore.tgBot.process;

import dev.chipichapa.memestore.tgBot.callback.ICallBack;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
@AllArgsConstructor
public class CallBackProcessor {
    private List<ICallBack> callBackList;

    public SendMessage process(Update update){
        SendMessage sm = new SendMessage();
        sm.setChatId(update.getCallbackQuery().getMessage().getChatId());
        sm.setText("Упс, что-то пошло не так");
        String input = update.getCallbackQuery().getData();
        if (input.startsWith("!")) {
            ICallBack callBack = callBackList.stream().filter(c->input.contains(c.getCallBack())).findFirst().orElse(null);
            if(callBack != null)
                return callBack.handle(update, sm);
        }
        return sm;
    }
}
