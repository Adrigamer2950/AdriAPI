package me.adrigamer2950.adriapi.api.command;

import me.adrigamer2950.adriapi.api.APIPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Used to create subcommands, it must be attached to a {@link Command} main class.
 *
 * @param <T> Your plugin's main class
 * @since 1.0.0
 */
@SuppressWarnings("unused")
public abstract class SubCommand<T extends APIPlugin> extends Command<T> {

    public SubCommand(Command<T> parent, String name) {
        this(parent, name, null);
    }

    public SubCommand(Command<T> parent, String name, @Nullable List<String> aliases) {
        super(parent.getPlugin(), name, aliases);
    }
}
