package me.devadri.obsidian.nms.common

import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.entity.Player

interface NmsSound {

    fun playToPlayer(player: Player, category: SoundCategory, sound: Sound, volume: Float, pitch: Float)
}