package me.adrigamer2950.adriapi.api.files;

import me.adrigamer2950.adriapi.AdriAPI;
import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.database.DatabaseManager;
import me.adrigamer2950.adriapi.api.exceptions.DuplicatedManagerException;
import me.adrigamer2950.adriapi.api.files.file.File;
import me.adrigamer2950.adriapi.api.logger.SubLogger;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class FileManager {

    public static final Logger LOGGER = new SubLogger("FileManager", AdriAPI.get().getLogger());
    public static final List<FileManager> FILE_MANAGERS = new ArrayList<>();
    public static FileManager getManager(Plugin plugin) {
        for(FileManager fM : FILE_MANAGERS)
            if(fM.getPlugin().equals(plugin))
                return fM;

        return null;
    }

    private final Plugin plugin;
    private final List<File> files = new ArrayList<>();

    public FileManager(Plugin plugin) {
        if(getManager(plugin) != null) {
            throw new DuplicatedManagerException(
                    String.format("File Manager for plugin %s v%s has already been created and cannot be duplicated",
                            plugin.getName(),
                            plugin.getDescription().getVersion()
                    )
            );
        }

        this.plugin = plugin;

        if(Boolean.parseBoolean(AdriAPI.get().configFile.get("debug").toString()))
            LOGGER.info(
                    Colors.translateColors(
                            String.format("File Manager for %s v%s has been successfully loaded", plugin.getName(), plugin.getDescription().getVersion())
                            , '&')
            );

        FILE_MANAGERS.add(this);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public void registerConfigFile(File file) {
        Validate.notNull(file, "File must not be null!");

        files.add(file);
    }

    public void createConfigFiles() throws IOException {
        for(File file : this.files)
            file.createFile();
    }

    public void saveConfigFiles() throws IOException {
        for(File file : this.files)
            file.save();
    }
}
