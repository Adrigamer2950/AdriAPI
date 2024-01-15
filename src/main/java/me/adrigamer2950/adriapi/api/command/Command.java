package me.adrigamer2950.adriapi.api.command;

import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.command.interfaces.TabCompleter;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
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
 * @see SubCommand
 */
@SuppressWarnings("unused")
public abstract class Command implements CommandExecutor, TabCompleter {

    private final String name;
    private final List<String> aliases;
    private final Plugin plugin;
    private List<SubCommand> subCommands;
    private boolean blockedForNonPlayers;
    private String blockedForNonPlayersMessage;
    private SubCommand helpSubCommand;

    protected Command(@NotNull Plugin pl, @NotNull String name) {
        this(pl, name, null);
    }

    protected Command(@NotNull Plugin pl, @NotNull String name, @Nullable List<String> aliases) {
        this(pl, name, aliases, new ArrayList<>());
    }

    public Command(@NotNull Plugin pl, @NotNull String name, @Nullable List<String> aliases, @Nullable List<SubCommand> subCommands) {
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

    public final Plugin getPlugin() {
        return this.plugin;
    }

    /**
     * You can use {@link Command#parseSubCommands(CommandSender, String, String[])} here to parse and execute subcommands attached to this main command,
     * if {@link Command#parseSubCommands(CommandSender, String, String[])} doesn't get any subcommand, it executes the help subcommand established with
     * {@link Command#setHelpSubCommand(SubCommand)}
     * @param sender Who sent the command
     * @param label The name of the command, it changes if an alias is used
     * @param args Arguments used on execution
     * @return The resolution of the execution, if false sends the usage message on your plugin.yml
     */
    public abstract boolean execute(CommandSender sender, String label, String[] args);

    @Nullable
    @Override
    public final List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull org.bukkit.command.Command command, @NotNull String s, @NotNull String[] strings) {
        return tabComplete(commandSender, s, strings);
    }

    /**
     * You can use {@link Command#parseSubCommandsTabCompleter(CommandSender, String, String[])} to parse suggestions for the tab completer
     * @param sender Who sent the command
     * @param label The name of the command, it changes if an alias is used
     * @param args Arguments used on execution
     * @return The list of suggestions for the tab completer
     */
    public abstract List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args);

    @Override
    public final boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player) && blockedForNonPlayers) {
            sender.sendMessage(this.blockedForNonPlayersMessage);
            return true;
        }

        return execute(sender, label, args);
    }

    /**
     * This method adds automatically the subcommand specified into the subcommand list,
     * so you don't need to do {@link Command#addSubCommand(SubCommand)} after adding the subcommand
     * @param helpSubCommand The subcommand to be used as a help command
     */
    protected final void setHelpSubCommand(SubCommand helpSubCommand) {
        Validate.notNull(helpSubCommand, "SubCommand must not be null!");

        this.subCommands.add(helpSubCommand);
        this.helpSubCommand = helpSubCommand;
    }

    protected final void addSubCommand(SubCommand subCommand) {
        Validate.notNull(subCommand, "SubCommand must not be null!");

        this.subCommands.add(subCommand);
    }

    protected final void setSubCommands(List<SubCommand> subCommands) {
        Validate.notNull(subCommands, "SubCommand List must not be null!");
        for (SubCommand scmd : this.subCommands)
            Validate.notNull(scmd, String.format("SubCommand '%s' must not be null!", scmd.getName()));

        this.subCommands = subCommands;
    }

    public final List<SubCommand> getSubCommands() {
        return this.subCommands;
    }

    /**
     * Parses the list of {@link SubCommand} you may have on this command to execute the one who has been executed by the sender
     * @param sender Who sent the command
     * @param label The name of the command, it changes if an alias is used
     * @param args Arguments used on execution
     * @return The resolution of the execution, if false sends the usage message on your plugin.yml
     */
    protected final boolean parseSubCommands(CommandSender sender, String label, String[] args) {
        Validate.notNull(this.subCommands, "SubCommand List is null!");

        for (SubCommand cmd : this.subCommands)
            for (String s : args)
                if (cmd.getName().equalsIgnoreCase(s) || (cmd.getAliases() != null && cmd.getAliases().contains(s)))
                    return cmd.execute(sender, label, args);

        if (this.helpSubCommand != null) return helpSubCommand.execute(sender, label, args);

        return false;
    }


    /**
     *
     * @param sender Who sent the command
     * @param label The name of the command, it changes if an alias is used
     * @param args Arguments used on execution
     * @return The list of suggestions for the tab completer
     */
    protected final List<String> parseSubCommandsTabCompleter(CommandSender sender, String label, String[] args) {
        Validate.notNull(this.subCommands, "SubCommand List is null!");

        for (SubCommand cmd : this.subCommands)
            for (int i = 0; i < args.length; i++)
                if (
                        cmd.getName().startsWith(args[i])
                                && args.length >= i + 2
                ) {
                    return cmd.tabComplete(sender, label, args);
                }

        List<String> strs = new ArrayList<>();

        this.subCommands.forEach(scmd -> strs.add(scmd.getName()));

        return strs;
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
