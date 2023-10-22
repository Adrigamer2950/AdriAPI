package me.adrigamer2950.adriapi.api.command;

import me.adrigamer2950.adriapi.api.command.interfaces.TabCompleter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used to create subcommands, it must be attached to a {@link Command} main class.
 */
@SuppressWarnings("unused")
public abstract class SubCommand extends Command implements TabCompleter {

    public SubCommand(Command parent, String name) {
        this(parent, name, null);
    }

    public SubCommand(Command parent, String name, @Nullable List<String> aliases) {
        super(parent.getPlugin(), name, aliases);
    }
}
