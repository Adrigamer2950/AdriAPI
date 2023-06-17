package me.adrigamer2950.adriapi.api.event.files;

import me.adrigamer2950.adriapi.api.files.file.File;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class FileEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }

    private final File file;

    public FileEvent(File file) {
        this.file = file;
    }


    public File getFile() {
        return file;
    }
}
