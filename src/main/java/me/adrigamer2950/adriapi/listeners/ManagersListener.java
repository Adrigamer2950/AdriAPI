package me.adrigamer2950.adriapi.listeners;

import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.config.manager.ConfigManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class ManagersListener implements org.bukkit.event.Listener {

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPluginDisable(PluginDisableEvent e) {
        if (CommandManager.getManager(e.getPlugin()) != null) {
            CommandManager.COMMAND_MANAGERS.remove(CommandManager.getManager(e.getPlugin()));
            CommandManager.LOGGER.log(String.format(
                    String.format("Command Manager for %s v%s has been successfully unloaded", e.getPlugin().getName(), e.getPlugin().getDescription().getVersion())
            ));
        }
        if (ConfigManager.getManager(e.getPlugin()) != null) {
            ConfigManager.CONFIG_MANAGERS.remove(ConfigManager.getManager(e.getPlugin()));
            ConfigManager.LOGGER.log(String.format(
                    String.format("Config Manager for %s v%s has been successfully unloaded", e.getPlugin().getName(), e.getPlugin().getDescription().getVersion())
            ));
        }
    }
}
