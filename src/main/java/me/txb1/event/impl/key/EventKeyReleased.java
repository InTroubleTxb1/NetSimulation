package me.txb1.event.impl.key;

import lombok.Getter;
import me.txb1.event.Event;

import java.awt.event.KeyEvent;

public class EventKeyReleased extends Event {
    @Getter
    private final KeyEvent keyEvent;
    public EventKeyReleased(final KeyEvent keyEvent){
        this.keyEvent=keyEvent;
    }
}
