package me.adrigamer2950.adriapi.api.user;

import me.adrigamer2950.adriapi.AdriAPI;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Optional;

/**
 * Represents a Player or a Console
 *
 * @since 2.0.0
 */
@SuppressWarnings("unused")
public interface User {

    /**
     * @param sender Bukkit API's command sender
     * @return A User
     */
    static User fromBukkitSender(CommandSender sender) {
        return new UserImpl(sender, AdriAPI.getPlugin(AdriAPI.class).getAdventure());
    }

    /**
     * @return True if the user is the console, false otherwise
     */
    boolean isConsole();

    /**
     * @return True if the user is a player, false otherwise
     */
    boolean isPlayer();

    /**
     * @return {@link Optional<ConsoleCommandSender>} of the Console
     */
    Optional<ConsoleCommandSender> getConsole();

    /**
     * @return {@link ConsoleCommandSender} if the user is the console, null otherwise
     */
    default ConsoleCommandSender getConsoleOrNull() {
        return getConsole().orElse(null);
    }

    /**
     * @return {@link Optional<Player>} of the Console
     */
    Optional<Player> getPlayer();

    /**
     * @return {@link Player} if the user is the console, null otherwise
     */
    default Player getPlayerOrNull() {
        return getPlayer().orElse(null);
    }

    /**
     * @param message The message you want to send
     */
    void sendMessage(String message);

    /**
     * @param messages The messages you want to send
     */
    void sendMessage(String... messages);

    /**
     * @param component The component you want to send
     */
    void sendMessage(Component component);

    /**
     * @param components The components you want to send
     */
    void sendMessage(Component... components);

    /**
     * @return The player's name
     */
    String getName();

    /**
     * @return The player's name
     * @see User#getName()
     */
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    Component name();

    /**
     * @param permission The permission
     * @return True if the user has the permission, false otherwise
     */
    boolean hasPermission(String permission);
}
