package me.adrigamer2950.adriapi.api.user

import me.adrigamer2950.adriapi.api.colors.Colors
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.jetbrains.annotations.ApiStatus
import java.util.*

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

    @Deprecated("Use User#asConsole instead", replaceWith = ReplaceWith("User#asConsole"))
    override fun getConsole(): Optional<ConsoleCommandSender> {
        if (isConsole()) return Optional.of(bukkitSender as ConsoleCommandSender)
        return Optional.empty()
    }

    @Deprecated("Use User#asPlayer instead", replaceWith = ReplaceWith("User#asPlayer"))
    override fun getPlayer(): Optional<Player> {
        if (isPlayer()) return Optional.of(bukkitSender as Player)
        return Optional.empty()
    }

    override fun sendMessage(vararg messages: String) {
        messages.forEach {
            bukkitSender.sendMessage(
                if (isConsole())
                    Colors.translateToAnsi(it)
                else
                    Colors.translateColors(it)
            )
        }
    }

    @Override
    override fun sendMessage(vararg components: Component) {
        if (isPlayer())
            components.forEach { bukkitSender.sendMessage(it) }
        else
            components.forEach {
                this.sendMessage(LegacyComponentSerializer.legacyAmpersand().serialize(it))
            }
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun name(): Component {
        return Component.text(this.name)
    }

    override fun hasPermission(permission: String): Boolean {
        return bukkitSender.hasPermission(permission)
    }
}
