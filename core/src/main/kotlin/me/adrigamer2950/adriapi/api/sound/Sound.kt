package me.adrigamer2950.adriapi.api.sound

import com.cryptomorin.xseries.XSound
import me.adrigamer2950.adriapi.api.ExperimentalAPI
import me.adrigamer2950.adriapi.api.Nms
import org.bukkit.Location
import org.bukkit.SoundCategory
import org.bukkit.entity.Entity
import org.bukkit.entity.Player

typealias BukkitSound = org.bukkit.Sound

typealias XCategory = XSound.Category

@Suppress("unused")
class Sound(
    val sound: XSound,
    val volume: Float = 1.0f,
    val pitch: Float = 1.0f,
    val category: XCategory = XCategory.MASTER,
) {

    fun playToEntity(entity: Entity) {
        if (entity is Player) {
            playToPlayer(entity)
        } else {
            playOnLocation(entity.location)
        }
    }

    @OptIn(ExperimentalAPI::class)
    fun playToPlayer(player: Player) {
        if (this.sound.get() == null)
            throw IllegalArgumentException("Sound ${this.sound.name()} is not valid")

        Nms.sound.playToPlayer(
            player,
            this.category.bukkitObject as? SoundCategory ?: SoundCategory.MASTER,
            this.sound.get()!!,
            this.volume,
            this.pitch
        )
    }

    fun playOnLocation(l: Location) {
        if (this.sound.get() == null)
            throw IllegalArgumentException("Sound ${this.sound.name()} is not valid")

        l.world.playSound(
            l,
            this.sound.get()!!,
            this.category.bukkitObject as? SoundCategory ?: SoundCategory.MASTER,
            this.volume,
            this.pitch
        )
    }

    class Builder {
        var sound: XSound? = null
            private set

        var volume: Float = 1.0f
            private set

        var pitch: Float = 1.0f
            private set

        var category: XCategory = XCategory.MASTER
            private set

        fun sound(sound: XSound): Builder {
            this.sound = sound
            return this
        }

        fun volume(volume: Float): Builder {
            this.volume = volume
            return this
        }

        fun pitch(pitch: Float): Builder {
            this.pitch = pitch
            return this
        }

        fun category(category: XCategory): Builder {
            this.category = category
            return this
        }

        fun build(): Sound {
            if (this.sound == null)
                throw IllegalArgumentException("Sound cannot be null")

            return Sound(this.sound!!, this.volume, this.pitch, this.category)
        }
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
