package me.adrigamer2950.adriapi.api.command.manager;

import me.adrigamer2950.adriapi.AdriAPI;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.event.command.CommandLoadedEvent;
import me.adrigamer2950.adriapi.api.exceptions.DuplicatedManagerException;
import me.adrigamer2950.adriapi.api.exceptions.command.CommandNotInPluginYMLException;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.adriapi.api.logger.SubLogger;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Manage commands.
 *
 * @author Adrigamer2950
 * @see Command
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public final class CommandManager {

    //    private final Logger LOGGER = new SubLogger("Command Manager", Bukkit.getPluginManager().getPlugin("AdriAPI").getLogger());
    public static final APILogger LOGGER = new SubLogger("CommandManager", AdriAPI.LOGGER);
    private final List<Command> cmds = new ArrayList<>();
    public static final List<CommandManager> COMMAND_MANAGERS = new ArrayList<>();

    public static CommandManager getManager(Plugin plugin) {
        for (CommandManager cmdM : COMMAND_MANAGERS)
            if (cmdM.getPlugin().equals(plugin))
                return cmdM;

        return null;
    }

    private final Plugin plugin;

    @SuppressWarnings("deprecation")
    public CommandManager(Plugin pl) {
        if (getManager(pl) != null) {
            throw new DuplicatedManagerException(
                    String.format("Command Manager for plugin %s v%s has already been created and cannot be duplicated",
                            pl.getName(),
                            pl.getDescription().getVersion()
                    )
            );
        }

        this.plugin = pl;

        COMMAND_MANAGERS.add(this);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * This method allows you to register and load commands for their use.
     *
     * @param command The command that you want to register and load.
     * @throws IllegalArgumentException       if command is null
     * @throws CommandNotInPluginYMLException if command is not registered in your plugin.yml
     * @since 1.0.0
     */
    public void registerCommand(Command command) {
        Validate.notNull(command, "Command must not be null");

        PluginCommand plCmd = command.getPlugin().getServer().getPluginCommand(command.getName());
        if (plCmd == null) {
            LOGGER.log(String.format("&cERROR LOADING COMMAND '%s'", command.getName()));
            throw new CommandNotInPluginYMLException(String.format("Command '%s' must be registered in plugin.yml", command.getName()));
        }

        cmds.add(command);

        plCmd.setExecutor(command);
        plCmd.setTabCompleter(command);

        CommandLoadedEvent event = new CommandLoadedEvent(command, plugin);
        Bukkit.getPluginManager().callEvent(event);

        LOGGER.log(
                String.format("Command '%s' for plugin %s v%s has been successfully loaded", command.getName(), command.getPlugin().getName(), command.getPlugin().getDescription().getVersion())
        );
    }

    /**
     * @param name The name of the command.
     * @return Registered command. Null if command doesn't exist.
     * @since 1.0.0
     */
    public Command getCommand(String name) {
        for (Command cmd : cmds) {
            if (!Objects.equals(cmd.getName(), name)) continue;

            return cmd;
        }

        return null;
    }
}
