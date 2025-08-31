package me.devadri.obsidian.sound

import com.cryptomorin.xseries.XSound
import me.devadri.obsidian.Nms
import org.bukkit.Location
import org.bukkit.SoundCategory
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

typealias BukkitSound = org.bukkit.Sound

typealias XSoundCategory = XSound.Category

@Suppress("unused")
class Sound(
    val sound: BukkitSound,
    val volume: Float = 1.0f,
    val pitch: Float = 1.0f,
    val category: XSoundCategory = XSoundCategory.MASTER,
) {

    constructor(
        sound: XSound,
        volume: Float = 1.0f,
        pitch: Float = 1.0f,
        category: XSoundCategory = XSoundCategory.MASTER
    ) : this(
        sound.get() ?: throw IllegalArgumentException("Sound ${sound.name()} is not valid"),
        volume,
        pitch,
        category
    )

    fun playToEntity(entity: Entity) {
        if (entity is Player) {
            playToPlayer(entity)
        } else {
            playOnLocation(entity.location)
        }
    }

    fun playToPlayer(player: Player) {
        Nms.sound.playToPlayer(
            player,
            this.category.bukkitObject as? SoundCategory ?: SoundCategory.MASTER,
            this.sound,
            this.volume,
            this.pitch
        )
    }

    fun playOnLocation(l: Location) {
        l.world.playSound(
            l,
            this.sound,
            this.category.bukkitObject as? SoundCategory ?: SoundCategory.MASTER,
            this.volume,
            this.pitch
        )
    }

    class Builder {
        var sound: BukkitSound? = null
            private set

        var volume: Float = 1.0f
            private set

        var pitch: Float = 1.0f
            private set

        var category: XSoundCategory = XSoundCategory.MASTER
            private set

        fun sound(sound: XSound): Builder = apply { this.sound = sound.get() ?: throw IllegalArgumentException("Sound ${sound.name()} is not valid") }

        fun sound(sound: BukkitSound): Builder = apply { this.sound = sound }

        fun volume(volume: Float): Builder = apply { this.volume = volume }

        fun pitch(pitch: Float): Builder = apply { this.pitch = pitch }

        fun category(category: XSoundCategory): Builder = apply { this.category = category }

        fun build(): Sound = Sound(this.sound!!, this.volume, this.pitch, this.category)
    }

    fun toBuilder(): Builder {
        return Builder()
            .sound(this.sound)
            .volume(this.volume)
            .pitch(this.pitch)
            .category(this.category)
    }

    companion object {
        @JvmStatic
        fun builder(): Builder {
            return Builder()
        }

        @JvmStatic
        fun fromBukkitSound(sound: BukkitSound): Sound {
            return Sound(XSound.of(sound))
        }
    }
}
