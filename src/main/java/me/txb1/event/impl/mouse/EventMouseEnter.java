package me.txb1.event.impl.mouse;

import lombok.Getter;
import me.txb1.event.Event;

import java.awt.event.MouseEvent;

public class EventMouseEnter extends Event {
    @Getter
    private final MouseEvent mouseEvent;
    public EventMouseEnter(final MouseEvent mouseEvent){
        this.mouseEvent=mouseEvent;
    }
}
