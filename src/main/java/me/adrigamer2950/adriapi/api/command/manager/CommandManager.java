package me.adrigamer2950.adriapi.api.command.manager;

import lombok.Getter;
import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.event.command.CommandLoadedEvent;
import me.adrigamer2950.adriapi.api.exceptions.DuplicatedManagerException;
import me.adrigamer2950.adriapi.api.exceptions.command.CommandNotInPluginYMLException;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;

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
    @ApiStatus.Internal
    public static final List<CommandManager<? extends APIPlugin>> COMMAND_MANAGERS = new ArrayList<>();

    /**
     * @param plugin The plugin
     * @return The plugin if it has a Command Manager, null otherwise
     */
    public static CommandManager<? extends APIPlugin> getManager(Plugin plugin) {
        for (CommandManager<? extends APIPlugin> cmdM : COMMAND_MANAGERS)
            if (cmdM.getPlugin().equals(plugin))
                return cmdM;

        return null;
    }

    @Getter
    private final T plugin;

    /**
     * @param pl The plugin
     */
    @SuppressWarnings("deprecation")
    public CommandManager(T pl) {
        if (getManager(pl) != null) {
            throw new DuplicatedManagerException(
                    String.format("Command Manager for plugin %s v%s has already been created and cannot be duplicated",
                            pl.getName(),
                            pl.getDescription().getVersion()
                    )
            );
        }

        this.plugin = pl;

        this.LOGGER = this.plugin.getLogger();

        COMMAND_MANAGERS.add(this);
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

        command.getPlugin().getServer().getCommandMap().register(command.getPlugin().getPluginMeta().getName(), command);

        cmds.add(command);

        CommandLoadedEvent event = new CommandLoadedEvent(command, plugin);
        Bukkit.getPluginManager().callEvent(event);

        //noinspection deprecation
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
