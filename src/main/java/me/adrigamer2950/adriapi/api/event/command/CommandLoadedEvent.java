package me.adrigamer2950.adriapi.api.event.command;

import lombok.Getter;
import me.adrigamer2950.adriapi.api.command.Command;
import org.bukkit.plugin.Plugin;

/**
 * This event will be triggered when a {@link Command} is successfully loaded
 *
 * @see Command
 */
@Getter
public class CommandLoadedEvent extends CommandEvent {

    private final Plugin loader;

    public CommandLoadedEvent(Command<?> command, Plugin loader) {
        super(command);
        this.loader = loader;
    }
}
