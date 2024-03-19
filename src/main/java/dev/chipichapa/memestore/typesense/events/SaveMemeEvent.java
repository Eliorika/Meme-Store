package dev.chipichapa.memestore.typesense.events;

import dev.chipichapa.memestore.typesense.dto.SavedMeme;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class SaveMemeEvent extends ApplicationEvent {

    private SavedMeme savedMeme;

    public SaveMemeEvent(Object source, SavedMeme savedMeme) {
        super(source);
        this.savedMeme = savedMeme;
    }
}
