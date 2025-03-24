package me.adrigamer2950.adriapi.api.command;

import lombok.Getter;
import lombok.NonNull;
import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.event.command.CommandLoadedEvent;
import me.adrigamer2950.adriapi.api.event.command.CommandUnloadedEvent;
import me.adrigamer2950.adriapi.api.user.User;
import me.adrigamer2950.adriapi.api.util.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Default implementation of {@link Command}
 *
 * @since 2.4.0
 */
@Getter
public abstract class AbstractCommand extends org.bukkit.command.Command implements Command {

    private final APIPlugin plugin;
    private final List<Command> subCommands;

    public AbstractCommand(@NotNull @NonNull APIPlugin plugin, @NotNull @NonNull String name) {
        this(plugin, name, List.of());
    }

    public AbstractCommand(@NotNull @NonNull APIPlugin plugin, @NotNull @NonNull String name, @NotNull @NonNull List<Command> subCommands) {
        this(plugin, name, "No description", List.of(), subCommands);
    }

    public AbstractCommand(
            @NotNull @NonNull APIPlugin plugin,
            @NotNull @NonNull String name,
            @NotNull @NonNull String description,
            @NotNull @NonNull List<String> aliases,
            @NotNull @NonNull List<Command> subCommands
    ) {
        super(name, description, "/" + name, aliases);

        this.subCommands = subCommands;
        this.plugin = plugin;
    }

    @Override
    public abstract void execute(@NotNull User user, @NotNull String[] args, @NotNull String commandName);

    @Override
    public final boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        this.execute(User.fromBukkitSender(sender), args, commandLabel);

        return true;
    }

    @Override
    public List<String> tabComplete(User user, String[] args, String commandName) {
        return List.of();
    }

    @Override
    public final @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String commandName, @NotNull String[] args) throws IllegalArgumentException {
        return tabComplete(User.fromBukkitSender(sender), args, commandName);
    }

    @SuppressWarnings("unused")
    protected void executeSubCommands(@NotNull @NonNull User user, @NotNull @NonNull String[] args, @NotNull @NonNull String commandName) {
        this.executeSubCommands(user, args, commandName, true);
    }

    protected void executeSubCommands(@NotNull @NonNull User user, @NotNull @NonNull String[] args, @NotNull @NonNull String commandName, @SuppressWarnings("SameParameterValue") boolean removeFirstArgument) {
        if (this.getSubCommands().isEmpty()) return;

        Optional<Command> help = this.findHelpCommand();

        if (args.length == 0) {
            if (help.isPresent()) help.get().execute(user, args, commandName);

            return;
        }

        String[] _args = args;
        Optional<Command> subCommand = this.getSubCommands().stream().filter(sub -> sub.getName().equalsIgnoreCase(_args[0])).findFirst();

        if (subCommand.isEmpty()) {
            if (help.isPresent()) help.get().execute(user, args, commandName);

            return;
        }

        if (removeFirstArgument) { // Remove first argument
            List<String> l = new ArrayList<>(Arrays.stream(args).toList());
            l.remove(0);
            args = l.toArray(value -> new String[l.size()]);
        }

        subCommand.get().execute(user, args, commandName);
    }

    private Optional<Command> findHelpCommand() {
        return this.getSubCommands().stream().filter(subCommand -> subCommand.getName().equalsIgnoreCase("help")).findFirst();
    }

    @Override
    public void unRegister() {
        CommandUtil.unRegisterCommand(this, plugin.getCommandManager());

        Bukkit.getPluginManager().callEvent(new CommandUnloadedEvent(this));
    }

    @Override
    public void register() {
        CommandUtil.registerCommand(this, plugin);

        Bukkit.getPluginManager().callEvent(new CommandLoadedEvent(this));
    }
}
