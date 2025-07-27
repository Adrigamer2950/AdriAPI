package me.adrigamer2950.adriapi.api.user

import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus
import java.util.*

/**
 * Represents a Player or a Console
 *
 * @since 2.0.0
 */
@Suppress("unused", "DEPRECATION")
interface User {

    val bukkitSender: CommandSender

    val name: String
        get() = bukkitSender.name

    fun isConsole(): Boolean

    fun isPlayer(): Boolean

    /**
     * @return Optional object of ConsoleCommandSender
     */
    @Deprecated("Use User#asConsole instead", ReplaceWith("User#asConsole"))
    fun getConsole(): Optional<ConsoleCommandSender>

    /**
     * @return A ConsoleCommandSender object if the user is the console, null otherwise
     */
    @Deprecated("Use User#asConsole instead", ReplaceWith("User#asConsole"))
    fun getConsoleOrNull(): ConsoleCommandSender? {
        return getConsole().orElse(null)
    }

    fun asConsole(): ConsoleCommandSender? {
        return bukkitSender as? ConsoleCommandSender
    }

    /**
     * @return Optional object of Player
     */
    @Deprecated("Use User#asPlayer instead", ReplaceWith("User#asPlayer"))
    fun getPlayer(): Optional<Player>

    /**
     * @return A Player object if the user is a player, null otherwise
     */
    @Deprecated("Use User#asPlayer instead", ReplaceWith("User#asPlayer"))
    fun getPlayerOrNull(): Player? {
        return getPlayer().orElse(null)
    }

    fun asPlayer(): Player? {
        return bukkitSender as? Player
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
     * @see User#name
     */
    @Deprecated("Use User#name instead", ReplaceWith("name"))
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    fun name(): Component

    /**
     * @param permission The permission
     * @return True if the user has the permission, false otherwise
     */
    fun hasPermission(permission: String): Boolean

    companion object {

        private val cachedUsers: Set<User> = HashSet()

        /**
         * @param sender Bukkit API's command sender
         * @return A User
         */
        @JvmStatic
        fun fromBukkitSender(sender: CommandSender): User {
            return cachedUsers.firstOrNull { it.bukkitSender == sender } ?: UserImpl(sender)
        }

        /**
         * @return The console as a User
         */
        @JvmStatic
        fun console(): User {
            return fromBukkitSender(Bukkit.getConsoleSender())
        }
    }
}
