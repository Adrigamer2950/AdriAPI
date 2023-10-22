package me.adrigamer2950.adriapi.api.command;

import me.adrigamer2950.adriapi.api.command.interfaces.TabCompleter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SubCommand extends Command implements TabCompleter {

    public SubCommand(Command parent, String name) {
        this(parent, name, null);
    }

    public SubCommand(Command parent, String name, @Nullable List<String> aliases) {
        super(parent.getPlugin(), name, aliases);
    }

    public abstract @Nullable List<String> tabComplete(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args);

    @Override
    public final @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, org.bukkit.command.@NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return this.tabComplete(commandSender, s, strings);
    }
}
