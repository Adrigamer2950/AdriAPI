package me.adrigamer2950.adriapi.api.command;

import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.command.interfaces.TabCompleter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used to create subcommands, it must be attached to a {@link Command} main class.
 * @since 1.0.0
 * @param <T> Your plugin's main class
 */
@SuppressWarnings("unused")
public abstract class SubCommand<T extends APIPlugin> extends Command<T> implements TabCompleter {

    public SubCommand(Command<T> parent, String name) {
        this(parent, name, null);
    }

    public SubCommand(Command<T> parent, String name, @Nullable List<String> aliases) {
        super(parent.getPlugin(), name, aliases);
    }
}
