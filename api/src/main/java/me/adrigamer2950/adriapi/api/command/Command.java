package me.adrigamer2950.adriapi.api.command;

import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.user.User;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Command interface. You can implement this interface
 * if you want to customize default behavior however
 * you see fit. Otherwise, you should extend {@link AbstractCommand}.
 *
 * @since 1.0.0
 */
public interface Command {

    @NotNull String getName();

    @NotNull APIPlugin getPlugin();

    @NotNull List<Command> getSubCommands();

    /**
     * Register the Command
     */
    void register();

    /**
     * Unregister the Command
     */
    void unRegister();

    /**
     * @param user The user that executed the command
     * @param args The arguments of the command
     * @param commandName The alias of the command
     */
    void execute(User user, String[] args, String commandName);

    /**
     * @param user The user that executed the command
     * @param args The arguments of the command
     * @param commandName The alias of the command
     * @return The list of suggestions
     */
    List<String> tabComplete(User user, String[] args, String commandName);
}
