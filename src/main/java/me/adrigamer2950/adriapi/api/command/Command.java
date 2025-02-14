package me.adrigamer2950.adriapi.api.command;

import lombok.Getter;
import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.user.User;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Used to create commands, with some
 * shortcuts to things that would be more
 * difficult to implement in normal Bukkit API.
 * Capable of implementing subcommands with just 1 line of code on command execution.
 * You may use {@link Command#addSubCommand(SubCommand)} or {@link Command#setSubCommands(List)} to add
 * subcommands to a command
 *
 * @param <T> Your plugin's main class
 * @see SubCommand
 * @see CommandManager
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class Command<T extends APIPlugin> extends org.bukkit.command.Command {

    @Getter
    private final String name;
    @Getter
    private final List<String> aliases;
    @Getter
    private final T plugin;
    @Getter
    private List<SubCommand<T>> subCommands;
    private SubCommand<T> helpSubCommand;

    /**
     * @param pl   The plugin
     * @param name The command's name
     */
    public Command(@NotNull T pl, @NotNull String name) {
        this(pl, name, null);
    }

    /**
     * @param pl      The plugin
     * @param name    The command's name
     * @param aliases The command's aliases
     */
    public Command(@NotNull T pl, @NotNull String name, @Nullable List<String> aliases) {
        this(pl, name, aliases, new ArrayList<>());
    }

    /**
     * @param pl          The plugin
     * @param name        The command's name
     * @param aliases     The command's aliases
     * @param subCommands The command's {@link SubCommand} list
     */
    public Command(@NotNull T pl, @NotNull String name, @Nullable List<String> aliases, @Nullable List<SubCommand<T>> subCommands) {
        super(name, "", "/%s".formatted(name), aliases == null ? new ArrayList<>() : aliases);
        this.plugin = pl;
        this.name = name;
        this.aliases = aliases == null ? new ArrayList<>() : aliases;
        this.subCommands = subCommands;
    }

    /**
     * You can use {@link Command#parseSubCommands(User, String, String[])} here to parse and execute subcommands attached to this main command,
     * if {@link Command#parseSubCommands(User, String, String[])} doesn't get any subcommand, it executes the help subcommand established with
     * {@link Command#setHelpSubCommand(SubCommand)}
     *
     * @param user  Who sent the command
     * @param label The name of the command, it changes if an alias is used
     * @param args  Arguments used on execution
     * @return The resolution of the execution, if false sends the usage message on your plugin.yml
     */
    public abstract boolean execute(User user, String label, String[] args);

    @Override
    public final boolean execute(@NotNull CommandSender sender, @NotNull String label, String[] args) {
        return execute(User.fromBukkitSender(sender), label, args);
    }

    @Override
    public final @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {
        return tabComplete(User.fromBukkitSender(sender), label, args);
    }

    /**
     * You can use {@link Command#parseSubCommandsTabCompleter(User, String, String[])} to parse suggestions for the tab completer
     *
     * @param user  Who sent the command
     * @param label The name of the command, it changes if an alias is used
     * @param args  Arguments used on execution
     * @return The list of suggestions for the tab completer
     */
    public List<String> tabComplete(@NotNull User user, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }

    /**
     * This method adds automatically the subcommand specified into the subcommand list,
     * so you don't need to do {@link Command#addSubCommand(SubCommand)} after adding the subcommand
     *
     * @param helpSubCommand The subcommand to be used as a help command
     */
    protected final void setHelpSubCommand(SubCommand<T> helpSubCommand) {
        this.addSubCommand(helpSubCommand);

        this.helpSubCommand = helpSubCommand;
    }

    protected final void addSubCommand(SubCommand<T> subCommand) {
        if (subCommand == null)
            throw new NullPointerException("SubCommand must not be null!");

        this.subCommands.add(subCommand);
    }

    protected final void setSubCommands(List<SubCommand<T>> subCommands) {
        if (subCommands == null)
            throw new NullPointerException("SubCommand list must not be null!");

        for (SubCommand<T> scmd : this.subCommands)
            if (scmd == null)
                throw new NullPointerException("There's some SubCommand that is null");

        this.subCommands = subCommands;
    }

    /**
     * Parses the list of {@link SubCommand} you may have on this command to execute the one who has been executed by the sender
     *
     * @param user  Who sent the command
     * @param label The name of the command, it changes if an alias is used
     * @param args  Arguments used on execution
     * @return The resolution of the execution, if false sends the usage message on your plugin.yml
     */
    protected final boolean parseSubCommands(User user, String label, String[] args) {
        if (subCommands == null)
            throw new NullPointerException("SubCommand list is null!");

        if (args.length == 0 && this.helpSubCommand != null)
            return helpSubCommand.execute(user, label, args);

        final String[] _args = args;
        Optional<SubCommand<T>> scmd = this.subCommands.stream().filter(
                cmd -> cmd.getName().equalsIgnoreCase(_args[0])
                        || (cmd.getAliases().contains(_args[0]))
        ).findFirst();

        if (scmd.isEmpty())
            if (helpSubCommand != null)
                return helpSubCommand.execute(user, label, args);
            else
                return false;

        List<String> l = new ArrayList<>(Arrays.stream(args).toList());
        l.remove(0);
        args = l.toArray(value -> new String[l.size()]);

        return scmd.get().execute(user, label, args);
    }


    /**
     * @param user  Who sent the command
     * @param label The name of the command, it changes if an alias is used
     * @param args  Arguments used on execution
     * @return The list of suggestions for the tab completer
     */
    protected final List<String> parseSubCommandsTabCompleter(User user, String label, String[] args) {
        if (this.getSubCommands() == null)
            throw new NullPointerException("SubCommand list is null!");

        if (args.length == 0 && this.helpSubCommand != null)
            return this.helpSubCommand.tabComplete(user, label, args);

        final String[] _args = args;
        Optional<SubCommand<T>> scmd = this.getSubCommands().stream().filter(
                cmd -> cmd.getName().equalsIgnoreCase(_args[0])
                        || (cmd.getAliases().contains(_args[0]))
        ).findFirst();

        if (scmd.isEmpty())
            if (this.helpSubCommand != null)
                return helpSubCommand.tabComplete(user, label, args);
            else
                return List.of();

        if (args.length < 2) {
            @NotNull String[] finalArgs = args;
            return this.getSubCommands().stream().map(Command::getName).filter(name -> name.toLowerCase().startsWith(finalArgs[0].toLowerCase())).toList();
        }

        List<String> l = new ArrayList<>(Arrays.stream(args).toList());
        l.remove(0);
        args = l.stream().map(String::valueOf).toArray(value -> new String[l.size()]);

        return scmd.get().tabComplete(user, label, args);
    }
}
