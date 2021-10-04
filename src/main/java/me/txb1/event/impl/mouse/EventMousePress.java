package me.txb1.event.impl.mouse;

import lombok.Getter;
import me.txb1.event.Event;

import java.awt.event.MouseEvent;

public class EventMousePress extends Event {
    @Getter
    private final MouseEvent mouseEvent;
    public EventMousePress(final MouseEvent mouseEvent){
        this.mouseEvent=mouseEvent;
    }
}
