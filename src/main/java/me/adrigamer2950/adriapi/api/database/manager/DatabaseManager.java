package me.adrigamer2950.adriapi.api.database.manager;

import me.adrigamer2950.adriapi.AdriAPI;
import me.adrigamer2950.adriapi.api.database.Database;
import me.adrigamer2950.adriapi.api.event.database.DatabaseLoadedEvent;
import me.adrigamer2950.adriapi.api.exceptions.DuplicatedManagerException;
import me.adrigamer2950.adriapi.api.logger.SubLogger;
import me.adrigamer2950.adriapi.api.colors.Colors;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

/**
 * Manage databases.
 * @since 1.0.0
 * @author Adrigamer2950
 * @see Database
 */
public class DatabaseManager {
    public static final Logger LOGGER = new SubLogger("DatabaseManager", AdriAPI.get().getLogger());
    private static final HashMap<Plugin, List<Database>> DBs = new HashMap<>();
    public static final List<DatabaseManager> DATABASE_MANAGERS = new ArrayList<>();
    public static DatabaseManager getManager(Plugin plugin) {
        for(DatabaseManager dbM : DATABASE_MANAGERS)
            if(dbM.getPlugin().equals(plugin))
                return dbM;

        return null;
    }
    public final Plugin plugin;

    public DatabaseManager(Plugin pl) {
        if(getManager(pl) != null) {
            throw new DuplicatedManagerException(
                    String.format("Database Manager for plugin %s v%s has already been created and cannot be duplicated",
                            pl.getName(),
                            pl.getDescription().getVersion()
                    )
            );
        }

        this.plugin = pl;

        DBs.put(pl, new ArrayList<>());

        if(Boolean.parseBoolean(AdriAPI.get().configFile.get("debug").toString()))
            LOGGER.info(
                    Colors.translateColors(
                            String.format("Database Manager for %s v%s has been successfully loaded", pl.getName(), pl.getDescription().getVersion())
                            , '&')
            );

        DATABASE_MANAGERS.add(this);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * This method allows you to register and load databases for their use.
     * @param database The database that you want to register and connect to.
     * @since 1.0.0
     * @exception IllegalArgumentException if command is null
     */
    public void registerDatabase(Database database) {
        Validate.notNull(database, "Database cannot be null");

        DatabaseLoadedEvent event = new DatabaseLoadedEvent(database, plugin);
        Bukkit.getPluginManager().callEvent(event);

        try {
            database.connect();
        } catch (SQLException e) {
            LOGGER.severe(
                    String.format("Database '%s' for plugin %s v%s has encountered and error",
                            database.getName(),
                            database.getPlugin().getName(),
                            database.getPlugin().getDescription().getVersion()
                    )
            );
            e.printStackTrace();
            return;
        }

        DBs.get(database.getPlugin()).add(database);

        LOGGER.info(
                Colors.translateColors(
                        String.format("Database '%s' for plugin %s v%s has been successfully loaded",
                                database.getName(),
                                database.getPlugin().getName(),
                                database.getPlugin().getDescription().getVersion()
                        ),
                        '&'
                )

        );
    }

    /**
     * @since 1.0.0
     * @param name The name of the database.
     * @return Registered database. Null if database doesn't exist.
     */
    public Database getDatabase(String name) {
        for(Database db : DBs.get(plugin)) {
            if(!Objects.equals(db.getName(), name)) continue;

            return db;
        }

        return null;
    }
}
