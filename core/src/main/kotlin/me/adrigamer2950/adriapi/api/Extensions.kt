package me.adrigamer2950.adriapi.api

import me.adrigamer2950.adriapi.api.user.User
import me.adrigamer2950.adriapi.api.user.impl.BukkitUser
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

fun CommandSender.toUser(): User = User.fromBukkitSender(this)

fun User.isConsole(): Boolean = (this is BukkitUser) && this.bukkitSender is ConsoleCommandSender

fun User.isPlayer(): Boolean = (this is BukkitUser) && this.bukkitSender is Player

fun User.asPlayer(): Player? = (this as? BukkitUser)?.bukkitSender as? Player