package me.adrigamer2950.adriapi.api.sound

import com.cryptomorin.xseries.XSound
import org.bukkit.Location
import org.bukkit.SoundCategory
import org.bukkit.entity.Entity

typealias BukkitSound = org.bukkit.Sound

@Suppress("unused")
class Sound(
    val sound: XSound,
    val volume: Float = 1.0f,
    val pitch: Float = 1.0f,
    val category: SoundCategory = SoundCategory.MASTER
) {

    fun playToEntity(entity: Entity) {
        if (this.sound.get() == null)
            throw IllegalArgumentException("Sound ${this.sound.name()} is not valid")

        entity.world.playSound(entity, this.sound.get()!!, this.category, this.volume, this.pitch)
    }

    fun playOnLocation(l: Location) {
        if (this.sound.get() == null)
            throw IllegalArgumentException("Sound ${this.sound.name()} is not valid")

        l.world.playSound(l, this.sound.get()!!, this.category, this.volume, this.pitch)
    }

    class Builder {
        var sound: XSound? = null
            private set

        var volume: Float = 1.0f
            private set

        var pitch: Float = 1.0f
            private set

        var category: SoundCategory = SoundCategory.MASTER
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

        fun category(category: SoundCategory): Builder {
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
