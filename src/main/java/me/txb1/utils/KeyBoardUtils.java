package me.txb1.utils;

import eu.firedata.system.controller.Controller;
import lombok.Getter;
import me.txb1.event.EventManager;
import me.txb1.event.impl.key.EventKeyPressed;
import me.txb1.event.impl.key.EventKeyTyped;
import me.txb1.event.impl.mouse.*;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class KeyBoardUtils {
    @Getter
    private static final ArrayList<Integer> pressedKeys = new ArrayList<>();

    public static boolean isKeyDown(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    private static final EventManager eventManager = Controller.getContext().getComponent(EventManager.class);

    //KeyboardEvents

    public static void keyPressed(final KeyEvent e) {
        if (!pressedKeys.contains((Object) e.getKeyCode())) {
            pressedKeys.add(e.getKeyCode());
        }
        eventManager.execute(new EventKeyPressed(e));
    }

    public static void keyTyped(final KeyEvent e) {
        eventManager.execute(new EventKeyTyped(e));
    }

    public static void keyReleased(final KeyEvent e) {
        if (pressedKeys.contains((Object) e.getKeyCode())) {
            pressedKeys.remove((Object) e.getKeyCode());
        }
        eventManager.execute(new EventKeyTyped(e));
    }

    //MouseEvents

    public static void mouseClicked(final MouseEvent e) {
        eventManager.execute(new EventMouseClick(e));
    }

    public static void mousePressed(final MouseEvent e) {
        eventManager.execute(new EventMousePress(e));
    }

    public static void mouseReleased(final MouseEvent e) {
        eventManager.execute(new EventMouseRelease(e));
    }

    public static void mouseEntered(final MouseEvent e) {
        eventManager.execute(new EventMouseEnter(e));
    }

    public static void mouseExited(final MouseEvent e) {
        eventManager.execute(new EventMouseExit(e));
    }
}