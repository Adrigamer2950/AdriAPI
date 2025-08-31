package me.devadri.obsidian.user

import me.devadri.obsidian.user.impl.BukkitUser
import net.kyori.adventure.text.Component
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import java.util.*

/**
 * Represents a Player or a Console
 *
 * @since 2.0.0
 */
@Suppress("unused", "DEPRECATION")
interface User {

    /**
     * @param messages The messages you want to send
     */
    fun sendRawMessage(vararg messages: String)

    /**
     * @param messages The messages you want to send
     */
    fun sendMessage(vararg messages: String)

    /**
     * @param components The components you want to send
     */
    fun sendMessage(vararg components: Component)

    /**
     * @param permission The permission
     * @return True if the user has the permission, false otherwise
     */
    fun hasPermission(permission: String): Boolean

    companion object {

        private val cachedUsers: Set<BukkitUser> = HashSet()

        /**
         * @param sender Bukkit API's command sender
         * @return A User
         */
        @JvmStatic
        fun fromBukkitSender(sender: CommandSender): User {
            return cachedUsers.firstOrNull { it.bukkitSender == sender } ?: BukkitUser(sender)
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
