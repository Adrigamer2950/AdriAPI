package me.adrigamer2950.adriapi.api.sound;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
@Value
@Builder
public class Sound {

    @NotNull
    @NonNull
    org.bukkit.Sound sound;

    @Builder.Default
    float volume = 1.0f;

    @Builder.Default
    float pitch = 1.0f;

    @NotNull
    @NonNull
    @Builder.Default
    SoundCategory category = SoundCategory.MASTER;

    public void playToEntity(Entity entity) {
        entity.getWorld().playSound(entity, this.sound, this.category, this.volume, this.pitch);
    }

    public void playOnLocation(Location l) {
        l.getWorld().playSound(l, this.sound, this.category, this.volume, this.pitch);
    }
}
