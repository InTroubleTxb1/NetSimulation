package me.txb1.event.impl.mouse;

import lombok.Getter;
import me.txb1.event.Event;

import java.awt.event.MouseEvent;

public class EventMouseClick extends Event {
    @Getter
    private final MouseEvent mouseEvent;
    public EventMouseClick(final MouseEvent mouseEvent){
        this.mouseEvent=mouseEvent;
    }
}
