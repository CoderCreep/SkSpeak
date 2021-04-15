package de.codercreep.skspeak.elements.events.bukkit;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PrivilegeKeyUsedEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
