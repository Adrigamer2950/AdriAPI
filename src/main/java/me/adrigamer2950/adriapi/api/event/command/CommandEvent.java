package me.adrigamer2950.adriapi.api.event.command;

import me.adrigamer2950.adriapi.api.command.Command;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CommandEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final Command command;

    public CommandEvent(Command command) {
        this.command = command;
    }


    public Command getCommand() {
        return command;
    }
}
