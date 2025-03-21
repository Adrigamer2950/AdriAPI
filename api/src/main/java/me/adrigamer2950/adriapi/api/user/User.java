package me.adrigamer2950.adriapi.api.user;

import lombok.NonNull;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    @NotNull
    static User fromBukkitSender(@NonNull CommandSender sender) {
        return new UserImpl(sender);
    }

    /**
     * @return The console as a User
     */
    @NotNull
    static User console() {
        return User.fromBukkitSender(Bukkit.getConsoleSender());
    }

    /**
     * @return The {@link CommandSender}
     */
    @NotNull
    CommandSender getBukkitSender();

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
    @NotNull
    Optional<ConsoleCommandSender> getConsole();

    /**
     * @return {@link ConsoleCommandSender} if the user is the console, null otherwise
     */
    @Nullable
    default ConsoleCommandSender getConsoleOrNull() {
        return getConsole().orElse(null);
    }

    /**
     * @return {@link Optional<Player>} of the Console
     */
    @NotNull
    Optional<Player> getPlayer();

    /**
     * @return {@link Player} if the user is the console, null otherwise
     */
    @Nullable
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
    @NotNull
    String getName();

    /**
     * @return The player's name
     * @see User#getName()
     */
    @Deprecated(forRemoval = true)
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    @NotNull
    Component name();

    /**
     * @param permission The permission
     * @return True if the user has the permission, false otherwise
     */
    boolean hasPermission(String permission);
}
