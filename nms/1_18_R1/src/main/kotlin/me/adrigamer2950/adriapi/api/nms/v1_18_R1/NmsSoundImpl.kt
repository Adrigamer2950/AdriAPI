@file:Suppress("unused")

package me.adrigamer2950.adriapi.api.nms.v1_18_R1

import me.adrigamer2950.adriapi.api.nms.common.NmsSound
import net.minecraft.core.Registry
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundSource
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer
import org.bukkit.entity.Player

class NmsSoundImpl : NmsSound {

    override fun playToPlayer(player: Player, category: SoundCategory, sound: Sound, volume: Float, pitch: Float) {
        val nmsSound = Registry.SOUND_EVENT.get(ResourceLocation(sound.key.key))
            ?: throw IllegalArgumentException("Sound ${sound.name} not found")

        val nmsCategory = when (category) {
            SoundCategory.MASTER -> SoundSource.MASTER
            SoundCategory.AMBIENT -> SoundSource.AMBIENT
            SoundCategory.BLOCKS -> SoundSource.BLOCKS
            SoundCategory.PLAYERS -> SoundSource.PLAYERS
            SoundCategory.HOSTILE -> SoundSource.HOSTILE
            SoundCategory.WEATHER -> SoundSource.WEATHER
            SoundCategory.NEUTRAL -> SoundSource.NEUTRAL
            SoundCategory.VOICE -> SoundSource.VOICE
            SoundCategory.RECORDS -> SoundSource.RECORDS
            SoundCategory.MUSIC -> SoundSource.MUSIC
            else -> throw IllegalArgumentException("Sound category ${category.name} not found")
        }

        val packet = ClientboundSoundEntityPacket(
            nmsSound,
            nmsCategory,
            (player as CraftPlayer).handle,
            volume,
            pitch
        )

        player.handle.connection.send(packet)
    }
}