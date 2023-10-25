package me.adrigamer2950.adriapi.api.files.yaml;

import me.adrigamer2950.adriapi.api.event.files.FileLoadedEvent;
import me.adrigamer2950.adriapi.api.files.File;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

/**
 * .yml configuration file class, used to create .yml files and manage their contents
 * @see File
 */
@SuppressWarnings("unused")
public class YamlFile extends File
{
    protected YamlConfiguration yaml;

    public YamlFile(final String path, final String name, final Plugin plugin) {
        super(path, name, plugin, FileType.YAML, true, true);
    }

    public YamlFile(final String path, final String name, final Plugin plugin, final boolean autoSaveOnServerShutdown) {
        super(path, name, plugin, FileType.YAML, autoSaveOnServerShutdown, true);
    }

    public YamlFile(final String path, final String name, final Plugin plugin, final boolean autoSaveOnServerShutdown, final boolean fileExistsOnPluginResources) {
        super(path, name, plugin, FileType.YAML, autoSaveOnServerShutdown, fileExistsOnPluginResources);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public void loadFile() throws IOException {
        final java.io.File f = new java.io.File(this.path, this.name + ".yml");
        if (!f.exists()) {
            new java.io.File(this.path).mkdirs();

            if(fileExistsOnPluginResources)
                Files.copy(Objects.requireNonNull(this.plugin.getResource(this.name + ".yml")), f.toPath());
            else {
                f.createNewFile();
            }
        }

        this.file = f;

        this.yaml = YamlConfiguration.loadConfiguration(f);

        Bukkit.getPluginManager().callEvent(new FileLoadedEvent(this, plugin));

        if(!fileExistsOnPluginResources) return;

        final Reader defConfigStream = new InputStreamReader(Objects.requireNonNull(this.plugin.getResource(this.name + ".yml")), StandardCharsets.UTF_8);
        final YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
        this.yaml.setDefaults(defConfig);
    }

    @Override
    public void saveFile() throws IOException {
        this.yaml.save(this.file);
    }

//    @Override
//    public String getString(final String path) {
//        return this.yaml.getString(path);
//    }
//
//    @Override
//    public List<String> getStringList(final String path) {
//        return this.yaml.getStringList(path);
//    }
//
//    @Override
//    public Boolean getBoolean(final String path) {
//        return this.yaml.getBoolean(path);
//    }
//
//    @Override
//    public List<Boolean> getBooleanList(final String path) {
//        return this.yaml.getBooleanList(path);
//    }
//
//    @Override
//    public Integer getInteger(final String path) {
//        return this.yaml.getInt(path);
//    }
//
//    @Override
//    public List<Integer> getIntegerList(final String path) {
//        return this.yaml.getIntegerList(path);
//    }
//
//    @Override
//    public Double getDouble(final String path) {
//        return this.yaml.getDouble(path);
//    }
//
//    @Override
//    public List<Double> getDoubleList(final String path) {
//        return this.yaml.getDoubleList(path);
//    }
//
//    @Override
//    public Long getLong(final String path) {
//        return this.yaml.getLong(path);
//    }
//
//    @Override
//    public List<Long> getLongList(final String path) {
//        return this.yaml.getLongList(path);
//    }
//
//    @Override
//    public Object get(final String path) {
//        return this.yaml.get(path);
//    }
//
//    @Override
//    public List<?> getList(final String path) {
//        return this.yaml.getList(path);
//    }

    public YamlConfiguration getYaml() {
        return this.yaml;
    }
}

