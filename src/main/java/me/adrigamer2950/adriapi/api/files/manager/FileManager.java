package me.adrigamer2950.adriapi.api.files.manager;

import me.adrigamer2950.adriapi.api.exceptions.DuplicatedManagerException;
import me.adrigamer2950.adriapi.api.files.File;
import me.adrigamer2950.adriapi.api.files.yaml.YamlFile;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Used to register and get easily configuration files, for example:
 * {@code FileManager.getManager(Bukkit.getPluginManager().getPlugin("MyPlugin")).getConfig("config"); }
 * would grant you access to that configuration file in order to edit its contents
 */
public class FileManager
{
    public static final List<FileManager> FILE_MANAGERS = new ArrayList<>();
    private final Plugin plugin;
    private final List<File> configs;

    public static FileManager getManager(Plugin plugin) {
        for (final FileManager fM : FileManager.FILE_MANAGERS) {
            if (fM.getPlugin().equals(plugin)) {
                return fM;
            }
        }
        return null;
    }

    public FileManager(Plugin plugin) {
        this.configs = new ArrayList<>();

        if (getManager(plugin) != null) {
            throw new DuplicatedManagerException(String.format("FileManager for plugin %s v%s has already been created and cannot be duplicated", plugin.getName(), plugin.getDescription().getVersion()));
        }

        this.plugin = plugin;

        FILE_MANAGERS.add(this);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public void registerConfigFile(File f) {
        Validate.notNull(f, "File must not be null!");

        this.configs.add(f);
    }

    public void createConfigFiles() throws IOException {
        for (File f : this.configs)
            f.loadFile();
    }

    public void saveConfigFiles() throws IOException {
        for (File f : this.configs)
            if (f.autoSaveOnServerShutdown)
                f.saveFile();
    }

    public YamlFile getConfig(String name) {
        for (File f : this.configs) {
            if (!(f instanceof YamlFile)) continue;
            if (Objects.equals(f.getName(), name)) return (YamlFile) f;
        }

        return null;
    }
}