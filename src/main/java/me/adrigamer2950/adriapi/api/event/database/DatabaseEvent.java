package me.adrigamer2950.adriapi.api.event.database;

import me.adrigamer2950.adriapi.api.database.Database;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class DatabaseEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Database database;

    public DatabaseEvent(Database database) {
        this.database = database;
    }


    public Database getDatabase() {
        return database;
    }
}
