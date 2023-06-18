package me.adrigamer2950.adriapi.api.files;

import me.adrigamer2950.adriapi.AdriAPI;
import me.adrigamer2950.adriapi.api.colors.Colors;
import me.adrigamer2950.adriapi.api.files.file.File;
import me.adrigamer2950.adriapi.api.logger.SubLogger;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class FileManager {

    public static final Logger LOGGER = new SubLogger("FileManager", AdriAPI.get().getLogger());
    public static HashMap<Plugin, FileManager> FILEManagers = new HashMap<>();
    public static FileManager getManager(Plugin plugin) {
        for(FileManager fileM : FILEManagers.values())
            if(fileM.getPlugin().equals(plugin))
                return fileM;

        return null;
    }

    private final Plugin plugin;
    private List<File> files;

    public FileManager(Plugin plugin) {
        this.plugin = plugin;

        if(Boolean.parseBoolean(AdriAPI.get().configFile.get("debug").toString()))
            LOGGER.info(
                    Colors.translateColors(
                            String.format("File Manager for %s v%s has been successfully loaded", plugin.getName(), plugin.getDescription().getVersion())
                            , '&')
            );
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public void registerConfigFile(File file) {
        Validate.notNull(file, "File must not be null!");

        files.add(file);
    }

    public void createConfigFiles(List<File> files) throws IOException {
        for(File file : files)
            file.createFile();
    }

    public void saveConfigFiles(List<File> files) throws IOException {
        for(File file : files)
            file.save();
    }
}
