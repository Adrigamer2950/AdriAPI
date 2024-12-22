package me.adrigamer2950.adriapi.api.event.command;

import lombok.Getter;
import me.adrigamer2950.adriapi.api.command.Command;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

/**
 * This event will be triggered when a {@link Command} is successfully loaded
 *
 * @see Command
 */
@Getter
public class CommandLoadedEvent extends CommandEvent {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Plugin loader;

    public CommandLoadedEvent(Command<?> command, Plugin loader) {
        super(command);
        this.loader = loader;
    }
}
