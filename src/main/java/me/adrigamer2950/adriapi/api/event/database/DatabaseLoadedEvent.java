package me.adrigamer2950.adriapi.api.event.database;

import me.adrigamer2950.adriapi.api.database.Database;
import org.bukkit.plugin.Plugin;

/**
 * This event will be triggered when a {@link Database} is successfully loaded
 * @see Database
 */
public class DatabaseLoadedEvent extends DatabaseEvent {

    private final Plugin loader;

    public DatabaseLoadedEvent(Database database, Plugin loader) {
        super(database);
        this.loader = loader;
    }

    public Plugin getLoader() {
        return loader;
    }
}