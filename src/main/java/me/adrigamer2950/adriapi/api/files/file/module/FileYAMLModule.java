package me.adrigamer2950.adriapi.api.files.file.module;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class FileYAMLModule implements FileModule {

    private FileConfiguration yaml;
    private Plugin plugin;

    public FileYAMLModule(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Object get(String path) {
        return yaml.get(path);
    }

    @Override
    public String getString(String path) {
        return String.valueOf(get(path));
    }

    @Override
    public Boolean getBoolean(String path) {
        return Boolean.parseBoolean((String) get(path));
    }

    @Override
    public Integer getInteger(String path) {
        return Integer.parseInt((String) get(path));
    }

    @Override
    public void loadConfiguration(File file) throws IOException {
        this.yaml = YamlConfiguration.loadConfiguration(file);

        if(!file.exists()) {
            Reader defConfigStream;
            defConfigStream = new InputStreamReader(Objects.requireNonNull(plugin.getResource(file.getName())), StandardCharsets.UTF_8);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            yaml.setDefaults(defConfig);

            yaml.options().copyDefaults(true);
            yaml.save(file);
        }
    }

    @Override
    public void saveFile(File file) throws IOException {
        this.yaml.save(file);
    }
}
