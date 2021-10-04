package me.txb1.event.impl.key;

import lombok.Getter;
import me.txb1.event.Event;

import java.awt.event.KeyEvent;

public class EventKeyPressed extends Event {
    @Getter
    private final KeyEvent keyEvent;
    public EventKeyPressed(final KeyEvent keyEvent){
        this.keyEvent=keyEvent;
    }
}
