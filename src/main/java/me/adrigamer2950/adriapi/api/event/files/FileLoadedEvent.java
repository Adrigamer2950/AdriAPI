package me.adrigamer2950.adriapi.api.event.files;

import me.adrigamer2950.adriapi.api.files.file.File;
import org.bukkit.plugin.Plugin;

public class FileLoadedEvent extends FileEvent {

    private final Plugin loader;

    public FileLoadedEvent(File file, Plugin loader) {
        super(file);
        this.loader = loader;
    }

    public Plugin getLoader() {
        return loader;
    }
}
