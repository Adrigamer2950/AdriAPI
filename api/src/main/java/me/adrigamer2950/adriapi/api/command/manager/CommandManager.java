package me.adrigamer2950.adriapi.api.command.manager;

import lombok.Getter;
import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.event.command.CommandLoadedEvent;
import me.adrigamer2950.adriapi.api.exceptions.command.CommandNotInPluginYMLException;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Manage commands.
 *
 * @param <T> Your plugin's main class
 * @see Command
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public final class CommandManager<T extends APIPlugin> {

    public final APILogger LOGGER;
    private final List<Command<? extends APIPlugin>> cmds = new ArrayList<>();

    @Getter
    private final T plugin;

    /**
     * @param pl The plugin
     */
    public CommandManager(T pl) {
        this.plugin = pl;

        this.LOGGER = this.plugin.getLogger();
    }

    /**
     * This method allows you to register and load commands for their use.
     *
     * @param command The command that you want to register and load.
     * @throws IllegalArgumentException       if command is null
     * @throws CommandNotInPluginYMLException if command is not registered in your plugin.yml
     * @since 1.0.0
     */
    public void registerCommand(Command<? extends APIPlugin> command) {
        if (command == null) {
            throw new NullPointerException("Command must not be null");
        }

        CommandMap commandMap = command.getPlugin().getServer().getCommandMap();

        if (commandMap.getCommand(command.getName()) == null) {
            commandMap.register(command.getPlugin().getDescription().getName(), command);
        } else {
            PluginCommand plCmd = command.getPlugin().getServer().getPluginCommand(command.getName());
            if (plCmd == null || plCmd.getPlugin() != command.getPlugin()) {
                LOGGER.error(String.format("&cERROR LOADING COMMAND '%s'", command.getName()));
                throw new CommandNotInPluginYMLException(String.format("Command '%s' must be registered in plugin.yml", command.getName()));
            }

            plCmd.setExecutor(command);
            plCmd.setTabCompleter(command);
        }

        cmds.add(command);

        CommandLoadedEvent event = new CommandLoadedEvent(command, plugin);
        Bukkit.getPluginManager().callEvent(event);

        LOGGER.debug(
                String.format("Command '%s' for plugin %s v%s has been successfully loaded", command.getName(), command.getPlugin().getName(), command.getPlugin().getDescription().getVersion())
        );
    }

    /**
     * @param name The name of the command.
     * @return Registered command. Null if command doesn't exist.
     * @since 1.0.0
     */
    public Command<? extends APIPlugin> getCommand(String name) {
        for (Command<? extends APIPlugin> cmd : cmds) {
            if (!Objects.equals(cmd.getName(), name)) continue;

            return cmd;
        }

        return null;
    }
}
