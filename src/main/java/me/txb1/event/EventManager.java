package me.txb1.event;

import eu.firedata.system.controller.annotations.type.Component;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@Getter
@Component
public class EventManager {
    private final List<Listener> listenerList = new ArrayList<>();

    public void removeListener(final Listener listener){
        this.listenerList.remove(listener);
    }
    public void registerListener(final Listener listener){
        this.listenerList.add(listener);
    }

    public void execute(final Event event){
        if (getListenerList().isEmpty())return;
        try {
            getListenerList().forEach(l -> {
                final Class clazz = l.getClass();
                for (final Method method : clazz.getMethods()) {
                    if (!method.isAnnotationPresent(EventHandler.class)) continue;
                    if (method.getParameterTypes()[0].getName().equalsIgnoreCase(event.getClass().getName())) {
                        try {
                            method.invoke(l, event);
                        } catch (IllegalAccessException | InvocationTargetException ignored) {
                        }
                    }
                }
            });
        }catch (ConcurrentModificationException ignored){}
    }
}
