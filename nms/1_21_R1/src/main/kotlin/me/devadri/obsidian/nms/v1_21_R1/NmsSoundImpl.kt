@file:Suppress("unused")

package me.devadri.obsidian.nms.v1_21_R1

import me.devadri.obsidian.nms.common.NmsSound
import net.minecraft.core.Holder
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.network.protocol.game.ClientboundSoundEntityPacket
import net.minecraft.sounds.SoundSource
import org.bukkit.Sound
import org.bukkit.SoundCategory
import org.bukkit.craftbukkit.CraftWorld
import org.bukkit.craftbukkit.entity.CraftPlayer
import org.bukkit.craftbukkit.util.CraftNamespacedKey
import org.bukkit.entity.Player

class NmsSoundImpl : NmsSound {

    override fun playToPlayer(player: Player, category: SoundCategory, sound: Sound, volume: Float, pitch: Float) {
        val nmsSound = BuiltInRegistries.SOUND_EVENT.get(CraftNamespacedKey.toMinecraft(sound.key))
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
            Holder.direct(nmsSound),
            nmsCategory,
            (player as CraftPlayer).handle,
            volume,
            pitch,
            (player.location.world as CraftWorld).handle.random.nextLong()
        )

        player.handle.connection.send(packet)
    }
}