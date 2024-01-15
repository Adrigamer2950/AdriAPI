package me.adrigamer2950.adriapi.api.event.config;

import me.adrigamer2950.adriapi.api.config.Config;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ConfigEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Config config;

    public ConfigEvent(Config config) {
        this.config = config;
    }


    public Config getFile() {
        return config;
    }
}
