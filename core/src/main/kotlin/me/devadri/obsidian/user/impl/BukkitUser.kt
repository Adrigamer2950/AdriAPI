package me.devadri.obsidian.user.impl

import me.devadri.obsidian.colors.Colors
import me.devadri.obsidian.user.User
import net.kyori.adventure.text.Component
import org.bukkit.command.CommandSender
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
class BukkitUser internal constructor(val bukkitSender: CommandSender) : User {

    override fun sendRawMessage(vararg messages: String) {
        bukkitSender.sendMessage(*messages)
    }

    override fun sendMessage(vararg messages: String) {
        messages.map { Colors.legacyToComponent(it) }.forEach { bukkitSender.sendMessage(it) }
    }

    override fun sendMessage(vararg components: Component) {
        components.forEach {
            bukkitSender.sendMessage(it)
        }
    }

    override fun hasPermission(permission: String): Boolean = bukkitSender.hasPermission(permission)
}