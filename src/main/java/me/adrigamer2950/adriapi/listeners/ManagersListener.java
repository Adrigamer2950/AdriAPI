package me.adrigamer2950.adriapi.listeners;

import lombok.RequiredArgsConstructor;
import me.adrigamer2950.adriapi.AdriAPI;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@RequiredArgsConstructor
public class ManagersListener implements org.bukkit.event.Listener {

    private final AdriAPI plugin;

    @EventHandler
    public void onPluginDisable(PluginDisableEvent e) {
        if (CommandManager.getManager(e.getPlugin()) != null) {
            CommandManager.COMMAND_MANAGERS.remove(CommandManager.getManager(e.getPlugin()));
            this.plugin.getLogger().info(String.format(
                    String.format("Command Manager for %s v%s has been successfully unloaded", e.getPlugin().getName(), e.getPlugin().getDescription().getVersion())
            ));
        }
    }
}
