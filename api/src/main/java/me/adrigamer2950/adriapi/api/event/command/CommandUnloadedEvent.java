package me.adrigamer2950.adriapi.api.event.command;

import lombok.Getter;
import me.adrigamer2950.adriapi.api.command.Command;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event will be triggered when a {@link Command} is unloaded
 *
 * @see Command
 */
@Getter
public class CommandUnloadedEvent extends CommandEvent {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    @SuppressWarnings("unused")
    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CommandUnloadedEvent(Command command) {
        super(command);
    }
}
