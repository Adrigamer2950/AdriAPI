package me.adrigamer2950.adriapi.api.user

import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

@Suppress("unused")
object UserFactory {

    fun fromBukkitSender(sender: CommandSender): User {
        return UserImpl(sender)
    }

    fun console(): User {
        return fromBukkitSender(Bukkit.getConsoleSender())
    }
}