package de.cooperr.cooperrutils.util;

import lombok.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
public class ConfigSetEvent extends Event {

    private final HandlerList handlerList = new HandlerList();

    private final String setKey;
    private final String setValue;

    public ConfigSetEvent(String setKey, String setValue) {
        this.setKey = setKey;
        this.setValue = setValue;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlerList;
    }
}
