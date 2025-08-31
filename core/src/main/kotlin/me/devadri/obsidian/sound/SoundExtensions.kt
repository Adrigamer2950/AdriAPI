@file:Suppress("unused")

package me.devadri.obsidian.sound

fun org.bukkit.Sound.toSound(): Sound = Sound.fromBukkitSound(this)

fun org.bukkit.Sound.toSoundBuilder(): Sound.Builder = toSound().toBuilder()