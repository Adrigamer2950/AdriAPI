package me.adrigamer2950.adriapi.api

import me.adrigamer2950.adriapi.api.user.User
import org.bukkit.command.CommandSender

fun CommandSender.toUser(): User = User.fromBukkitSender(this)