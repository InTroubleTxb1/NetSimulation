package me.txb1.event.impl.mouse;

import lombok.Getter;
import me.txb1.event.Event;

import java.awt.event.MouseEvent;

public class EventMouseExit extends Event {
    @Getter
    private final MouseEvent mouseEvent;
    public EventMouseExit(final MouseEvent mouseEvent){
        this.mouseEvent=mouseEvent;
    }
}
