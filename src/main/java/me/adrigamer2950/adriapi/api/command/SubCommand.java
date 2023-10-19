package me.adrigamer2950.adriapi.api.command;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class SubCommand extends Command {
    public SubCommand(Command parent, String name) {
        this(parent, name, null);
    }

    public SubCommand(Command parent, String name, @Nullable List<String> aliases) {
        super(parent.getPlugin(), name, aliases);
    }

    @Nullable
    public final List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String alias, @NotNull String[] args) {
        return null;
    }
}
