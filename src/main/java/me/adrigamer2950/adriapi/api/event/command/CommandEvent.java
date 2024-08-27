package me.adrigamer2950.adriapi.api.event.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.adrigamer2950.adriapi.api.command.Command;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * @see Command
 */
@Getter
@RequiredArgsConstructor
public class CommandEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {return handlers;}

    private final Command command;
}
