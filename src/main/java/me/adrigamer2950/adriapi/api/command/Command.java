package me.adrigamer2950.adriapi.api.command;

import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.interfaces.TabCompleter;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.user.User;
import me.adrigamer2950.adriapi.api.user.UserImpl;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to create commands, with some
 * shortcuts to things that would be more
 * difficult to implement in normal Bukkit API.
 * Capable of implementing subcommands with just 1 line of code on command execution.
 * You may use {@link Command#addSubCommand(SubCommand)} or {@link Command#setSubCommands(List)} to add
 * subcommands to a command
 *
 * @author Adrigamer2950
 * @see SubCommand
 * @see CommandManager
 * @since 1.0.0
 * @param <T> Your plugin's main class
 */
@SuppressWarnings("unused")
public abstract class Command<T extends APIPlugin> implements CommandExecutor, TabCompleter {

    private final String name;
    private final List<String> aliases;
    private final T plugin;
    private List<SubCommand<T>> subCommands;
    private boolean blockedForNonPlayers;
    private String blockedForNonPlayersMessage;
    private SubCommand<T> helpSubCommand;

    public Command(@NotNull T pl, @NotNull String name) {
        this(pl, name, null);
    }

    public Command(@NotNull T pl, @NotNull String name, @Nullable List<String> aliases) {
        this(pl, name, aliases, new ArrayList<>());
    }

    public Command(@NotNull T pl, @NotNull String name, @Nullable List<String> aliases, @Nullable List<SubCommand<T>> subCommands) {
        this.plugin = pl;
        this.name = name;
        this.aliases = aliases;
        this.subCommands = subCommands;
        this.blockedForNonPlayers = false;
        this.blockedForNonPlayersMessage = Colors.translateColors("&cCommand is blocked for non players");
    }

    public final String getName() {
        return this.name;
    }

    public final List<String> getAliases() {
        return this.aliases;
    }

    public final T getPlugin() {
        return this.plugin;
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
        if (!(sender instanceof Player) && blockedForNonPlayers) {
            sender.sendMessage(this.blockedForNonPlayersMessage);
            return true;
        }

        return execute(new UserImpl(sender), label, args);
    }

    @Nullable
    @Override
    public final List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        return tabComplete(new UserImpl(commandSender), s, strings);
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
        return null;
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

    public final List<SubCommand<T>> getSubCommands() {
        return this.subCommands;
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

        for (SubCommand<T> cmd : this.subCommands)
            for (String s : args)
                if (cmd.getName().equalsIgnoreCase(s) || (cmd.getAliases() != null && cmd.getAliases().contains(s)))
                    return cmd.execute(user, label, args);

        if (this.helpSubCommand != null) return helpSubCommand.execute(user, label, args);

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

        if (args.length > 1 && args[0].isEmpty())
            for (SubCommand<T> cmd : this.subCommands)
                if (cmd.getName().equals(args[0]))
                    return cmd.tabComplete(user, label, args);

        if (args.length < 2) {
            return this.subCommands.stream().map(Command::getName).filter(name -> name.toLowerCase().startsWith(args[0].toLowerCase())).toList();
        }

        return null;
    }

    protected final boolean isBlockedForNonPlayers() {
        return this.blockedForNonPlayers;
    }

    protected final void setBlockForNonPlayers(boolean blocked) {
        this.blockedForNonPlayers = blocked;
    }

    protected final String getBlockedForNonPlayersMessage() {
        return this.blockedForNonPlayersMessage;
    }

    protected final void setBlockForNonPlayersMessage(String message) {
        this.blockedForNonPlayersMessage = message;
    }
}
