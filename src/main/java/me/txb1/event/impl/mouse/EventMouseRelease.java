package me.txb1.event.impl.mouse;

import lombok.Getter;
import me.txb1.event.Event;

import java.awt.event.MouseEvent;

public class EventMouseRelease extends Event {
    @Getter
    private final MouseEvent mouseEvent;
    public EventMouseRelease(final MouseEvent mouseEvent){
        this.mouseEvent=mouseEvent;
    }
}
