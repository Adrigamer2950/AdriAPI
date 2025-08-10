package me.adrigamer2950.adriapi.api.user

import me.adrigamer2950.adriapi.api.colors.Colors
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus

/**
 * Default implementation of User
 *
 * @see User
 * @since 2.0.0
 */
@ApiStatus.Internal
class UserImpl internal constructor(override val bukkitSender: CommandSender) : User {

    override fun isConsole(): Boolean {
        return bukkitSender is ConsoleCommandSender
    }

    override fun isPlayer(): Boolean {
        return bukkitSender is Player
    }

    override fun sendMessage(vararg messages: String) {
        bukkitSender.sendMessage(
            *messages.map { if (isConsole()) Colors.legacyToAnsi(it) else Colors.legacy(it) }.toTypedArray()
        )
    }

    @Override
    override fun sendMessage(vararg components: Component) {
        if (isPlayer())
            components.forEach { bukkitSender.sendMessage(it) }
        else
            this.sendMessage(*components.map { LegacyComponentSerializer.legacyAmpersand().serialize(it) }.toTypedArray())
    }

    override fun hasPermission(permission: String): Boolean {
        return bukkitSender.hasPermission(permission)
    }
}
