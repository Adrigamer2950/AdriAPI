package me.adrigamer2950.adriapi.api.event.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.adrigamer2950.adriapi.api.command.Command;
import org.bukkit.event.Event;

/**
 * @see Command
 */
@Getter
@RequiredArgsConstructor
public abstract class CommandEvent extends Event {

    private final Command command;
}
