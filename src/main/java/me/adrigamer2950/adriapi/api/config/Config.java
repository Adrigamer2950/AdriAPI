package me.adrigamer2950.adriapi.api.config;

import me.adrigamer2950.adriapi.api.config.yaml.YamlConfig;
import org.bukkit.plugin.Plugin;

/**
 * Class to create configuration files, mainly .yml files
 * @see YamlConfig
 */
@SuppressWarnings("unused")
public abstract class Config
{
    protected final String path;
    protected final String name;
    protected java.io.File file;
    protected final Plugin plugin;
    public final boolean autoSaveOnServerShutdown;
    public final boolean fileExistsOnPluginResources;

    public Config(final String path, final String name, final Plugin plugin, final boolean autoSaveOnServerShutdown, final boolean fileExistsOnPluginResources) {
        this.path = path;
        this.name = name;
        this.plugin = plugin;
        this.autoSaveOnServerShutdown = autoSaveOnServerShutdown;
        this.fileExistsOnPluginResources = fileExistsOnPluginResources;
    }

    /**
     * Loads the file and its contents
     */
    public abstract void loadConfig() throws Throwable;

    /**
     * Saves the content stored into an actual file
     */
    public abstract void saveConfig() throws Throwable;

    public final Plugin getPlugin() {
        return this.plugin;
    }

    public final String getPath() {
        return this.path;
    }

    public final String getName() {
        return this.name;
    }

    public final boolean fileExists() {
        return this.file.exists();
    }

//    public abstract String getString(final String p0);
//
//    public abstract List<String> getStringList(final String p0);
//
//    public abstract Boolean getBoolean(final String p0);
//
//    public abstract List<Boolean> getBooleanList(final String p0);
//
//    public abstract Integer getInteger(final String p0);
//
//    public abstract List<Integer> getIntegerList(final String p0);
//
//    public abstract Double getDouble(final String p0);
//
//    public abstract List<Double> getDoubleList(final String p0);
//
//    public abstract Long getLong(final String p0);
//
//    public abstract List<Long> getLongList(final String p0);
//
//    public abstract Object get(final String p0);
//
//    public abstract List<?> getList(final String p0);
}
