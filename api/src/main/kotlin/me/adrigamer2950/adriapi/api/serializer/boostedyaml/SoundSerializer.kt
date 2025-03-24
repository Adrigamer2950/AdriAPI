package me.adrigamer2950.adriapi.api.serializer.boostedyaml;

import dev.dejvokep.boostedyaml.serialization.standard.TypeAdapter;
import me.adrigamer2950.adriapi.api.sound.Sound;
import org.bukkit.SoundCategory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class SoundSerializer implements TypeAdapter<Sound> {

    @Override
    public @NotNull Map<Object, Object> serialize(@NotNull Sound sound) {
        Map<Object, Object> map = new HashMap<>();

        map.put("sound", sound.getSound().name());

        if (sound.getVolume() != 1.0) {
            map.put("volume", sound.getVolume());
        }

        if (sound.getPitch() != 1.0) {
            map.put("pitch", sound.getPitch());
        }

        map.put("category", sound.getCategory().name());

        return map;
    }

    @Override
    public @NotNull Sound deserialize(@NotNull Map<Object, Object> map) {
        org.bukkit.Sound sound = org.bukkit.Sound.valueOf((String) map.get("sound"));

        float volume = 1.0f;
        if (map.containsKey("volume")) {
            volume = Float.parseFloat(map.get("volume").toString());
        }

        float pitch = 1.0f;
        if (map.containsKey("pitch")) {
            pitch = Float.parseFloat(map.get("pitch").toString());
        }

        SoundCategory category = SoundCategory.valueOf((String) map.get("category"));

        return Sound.builder()
                .sound(sound)
                .volume(volume)
                .pitch(pitch)
                .category(category)
                .build();
    }
}
