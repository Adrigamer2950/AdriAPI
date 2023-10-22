package me.adrigamer2950.adriapi.listeners;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.database.manager.DatabaseManager;
import me.adrigamer2950.adriapi.api.files.manager.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ManagersListener implements org.bukkit.event.Listener {

    @EventHandler
    public void onPluginDisable(PluginDisableEvent e) {
        if(CommandManager.getManager(e.getPlugin()) != null) {
            CommandManager.COMMAND_MANAGERS.remove(CommandManager.getManager(e.getPlugin()));
            CommandManager.LOGGER.log(String.format(
                    String.format("Command Manager for %s v%s has been successfully unloaded", e.getPlugin().getName(), e.getPlugin().getDescription().getVersion())
            ));
        }
        if(DatabaseManager.getManager(e.getPlugin()) != null) {
            DatabaseManager.DATABASE_MANAGERS.remove(DatabaseManager.getManager(e.getPlugin()));
            DatabaseManager.LOGGER.log(String.format(
                    String.format("Database Manager for %s v%s has been successfully unloaded", e.getPlugin().getName(), e.getPlugin().getDescription().getVersion())
            ));
        }
        if(FileManager.getManager(e.getPlugin()) != null) {
            FileManager.FILE_MANAGERS.remove(FileManager.getManager(e.getPlugin()));
            DatabaseManager.LOGGER.log(String.format(
                    String.format("Database Manager for %s v%s has been successfully unloaded", e.getPlugin().getName(), e.getPlugin().getDescription().getVersion())
            ));
        }
    }
}
