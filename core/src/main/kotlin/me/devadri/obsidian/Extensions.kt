package me.devadri.obsidian

import me.devadri.obsidian.user.User
import me.devadri.obsidian.user.impl.BukkitUser
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player

fun CommandSender.toUser(): User = User.fromBukkitSender(this)

fun User.isConsole(): Boolean = (this is BukkitUser) && this.bukkitSender is ConsoleCommandSender

fun User.isPlayer(): Boolean = (this is BukkitUser) && this.bukkitSender is Player

fun User.asPlayer(): Player? = (this as? BukkitUser)?.bukkitSender as? Player