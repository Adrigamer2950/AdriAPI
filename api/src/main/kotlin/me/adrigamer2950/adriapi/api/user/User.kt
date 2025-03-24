package me.adrigamer2950.adriapi.api.user

import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus

import java.util.Optional

/**
 * Represents a Player or a Console
 *
 * @since 2.0.0
 */
@SuppressWarnings("unused")
interface User {

    val bukkitSender: CommandSender

    val name: String
        get() = bukkitSender.name

    fun isConsole(): Boolean

    fun isPlayer(): Boolean

    /**
     * @return {@link Optional<ConsoleCommandSender>} of the Console
     */
    fun getConsole(): Optional<ConsoleCommandSender>

    /**
     * @return {@link ConsoleCommandSender} if the user is the console, null otherwise
     */
    fun getConsoleOrNull(): ConsoleCommandSender? {
        return getConsole().orElse(null)
    }

    /**
     * @return {@link Optional<Player>} of the Console
     */
    fun getPlayer(): Optional<Player>

    /**
     * @return {@link Player} if the user is the console, null otherwise
     */
    fun getPlayerOrNull(): Player? {
        return getPlayer().orElse(null)
    }

    /**
     * @param messages The messages you want to send
     */
    fun sendMessage(vararg messages: String)

    /**
     * @param components The components you want to send
     */
    fun sendMessage(vararg components: Component)

    /**
     * @return The player's name
     * @see User#getName()
     */
    @Deprecated("Use User#name instead", ReplaceWith("name"))
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    fun name(): Component

    /**
     * @param permission The permission
     * @return True if the user has the permission, false otherwise
     */
    fun hasPermission(permission: String): Boolean
}
