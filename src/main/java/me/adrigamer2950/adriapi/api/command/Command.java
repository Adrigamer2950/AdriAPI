package me.adrigamer2950.adriapi.api.command;

import me.adrigamer2950.adriapi.api.command.interfaces.TabCompleter;
import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class Command implements CommandExecutor, TabCompleter {

    private final String name;
    private final List<String> aliases;
    private final Plugin plugin;
    private List<SubCommand> subCommands;

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

    public abstract boolean execute(CommandSender sender, String label, String[] args);

    @Override
    public final boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        return execute(sender, label, args);
    }

    protected final void addSubCommand(SubCommand subCommand) {
        Validate.notNull(subCommand, "SubCommand must not be null!");

        this.subCommands.add(subCommand);
    }

    protected final void setSubCommands(List<SubCommand> subCommands) {
        Validate.notNull(subCommands, "SubCommand List must not be null!");
        for(SubCommand scmd : this.subCommands)
            Validate.notNull(scmd, String.format("SubCommand '%s' must not be null!", scmd.getName()));

        this.subCommands = subCommands;
    }

    public final List<SubCommand> getSubCommands() {
        return this.subCommands;
    }

    protected final boolean parseSubCommands(CommandSender sender, String label, String[] args) {
        Validate.notNull(this.subCommands, "SubCommand List is null!");

        for(SubCommand cmd : this.subCommands)
            for(String s : args)
                if(cmd.getName().equalsIgnoreCase(s) || cmd.getAliases().contains(s))
                    return cmd.execute(sender, label, args);

        return false;
    }
}
