@file:Suppress("unused")

package me.adrigamer2950.adriapi.api.sound

fun org.bukkit.Sound.toSound(): Sound = Sound.fromBukkitSound(this)

fun org.bukkit.Sound.toSoundBuilder(): Sound.Builder = toSound().toBuilder()