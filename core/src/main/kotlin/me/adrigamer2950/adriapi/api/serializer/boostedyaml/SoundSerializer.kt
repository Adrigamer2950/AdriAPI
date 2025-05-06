package me.adrigamer2950.adriapi.api.serializer.boostedyaml

import com.cryptomorin.xseries.XSound
import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter
import me.adrigamer2950.adriapi.api.sound.Sound
import me.adrigamer2950.adriapi.api.sound.XSoundCategory

@Suppress("unused")
class SoundSerializer : TypeAdapter<Sound> {

    override fun serialize(sound: Sound): Map<in Any, Any?> {
        val map: MutableMap<Any, Any> = LinkedHashMap()

        map["sound"] = sound.sound.name()

        if (sound.volume != 1.0f) {
            map["volume"] = sound.volume
        }

        if (sound.pitch != 1.0f) {
            map["pitch"] = sound.pitch
        }

        map["category"] = sound.category.name

        return map
    }

    override fun deserialize(map: Map<in Any, Any?>): Sound {
        val sound = XSound.of(map["sound"] as String)

        if (sound.isEmpty) {
            throw IllegalArgumentException("Sound ${map["sound"]} is not valid")
        }

        var volume = 1.0f
        if (map.containsKey("volume")) {
            volume = (map["volume"] as Float)
        }

        var pitch = 1.0f
        if (map.containsKey("pitch")) {
            pitch = (map["pitch"] as Float)
        }

        val category = XSoundCategory.valueOf(map["category"] as String)

        return Sound.builder()
            .sound(sound.get())
            .volume(volume)
            .pitch(pitch)
            .category(category)
            .build()
    }
}
