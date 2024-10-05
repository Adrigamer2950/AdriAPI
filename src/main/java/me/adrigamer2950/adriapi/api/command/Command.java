package me.adrigamer2950.adriapi.api.command;

import lombok.Getter;
import me.adrigamer2950.adriapi.api.APIPlugin;
import org.bukkit.command.TabCompleter;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.user.User;
import me.adrigamer2950.adriapi.api.user.UserImpl;
import org.bukkit.command.CommandExecutor;
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
 * @see SubCommand
 * @see CommandManager
 * @since 1.0.0
 * @param <T> Your plugin's main class
 */
@SuppressWarnings("unused")
public abstract class Command<T extends APIPlugin> implements CommandExecutor, TabCompleter {

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
     * @param pl The plugin
     * @param name The command's name
     */
    public Command(@NotNull T pl, @NotNull String name) {
        this(pl, name, null);
    }

    /**
     * @param pl The plugin
     * @param name The command's name
     * @param aliases The command's aliases
     */
    public Command(@NotNull T pl, @NotNull String name, @Nullable List<String> aliases) {
        this(pl, name, aliases, new ArrayList<>());
    }

    /**
     * @param pl The plugin
     * @param name The command's name
     * @param aliases The command's aliases
     * @param subCommands The command's {@link SubCommand} list
     */
    public Command(@NotNull T pl, @NotNull String name, @Nullable List<String> aliases, @Nullable List<SubCommand<T>> subCommands) {
        this.plugin = pl;
        this.name = name;
        this.aliases = aliases;
        this.subCommands = subCommands;
    }

    /**
     * You can use {@link Command#parseSubCommands(User, String, String[])} here to parse and execute subcommands attached to this main command,
     * if {@link Command#parseSubCommands(User, String, String[])} doesn't get any subcommand, it executes the help subcommand established with
     * {@link Command#setHelpSubCommand(SubCommand)}
     *
     * @param user Who sent the command
     * @param label  The name of the command, it changes if an alias is used
     * @param args   Arguments used on execution
     * @return The resolution of the execution, if false sends the usage message on your plugin.yml
     */
    public abstract boolean execute(User user, String label, String[] args);

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, String[] args) {
        return execute(new UserImpl(sender, getPlugin().getAdventure()), label, args);
    }

    @Nullable
    @Override
    public final List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        return tabComplete(new UserImpl(commandSender, getPlugin().getAdventure()), s, strings);
    }

    /**
     * You can use {@link Command#parseSubCommandsTabCompleter(User, String, String[])} to parse suggestions for the tab completer
     *
     * @param user Who sent the command
     * @param label  The name of the command, it changes if an alias is used
     * @param args   Arguments used on execution
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
     * @param user Who sent the command
     * @param label  The name of the command, it changes if an alias is used
     * @param args   Arguments used on execution
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
                        || (cmd.getAliases() != null && cmd.getAliases().contains(_args[0]))
        ).findFirst();

        if (scmd.isEmpty() && this.helpSubCommand != null)
            return helpSubCommand.execute(user, label, args);

        List<String> l = new ArrayList<>(Arrays.stream(args).toList());
        l.remove(0);
        //noinspection DataFlowIssue
        args = (String[]) l.toArray();

        //noinspection OptionalGetWithoutIsPresent
        scmd.get().execute(user, label, args);

        return false;
    }


    /**
     * @param user Who sent the command
     * @param label  The name of the command, it changes if an alias is used
     * @param args   Arguments used on execution
     * @return The list of suggestions for the tab completer
     */
    protected final List<String> parseSubCommandsTabCompleter(User user, String label, String[] args) {
        if (subCommands == null)
            throw new NullPointerException("SubCommand list is null!");

        List<String> l = new ArrayList<>(Arrays.asList(args));
        l.remove(0);
        //noinspection ToArrayCallWithZeroLengthArrayArgument
        final String[] args2 = l.toArray(new String[l.size()]);

        if (args.length > 1 && !args[0].isEmpty())
            for (SubCommand<T> cmd : this.subCommands)
                if (cmd.getName().equals(args[0]))
                    return cmd.tabComplete(user, label, args2);

        if (args.length < 2) {
            return this.subCommands.stream().map(Command::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        }

        return null;
    }
}
