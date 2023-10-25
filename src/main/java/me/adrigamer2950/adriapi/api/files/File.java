package me.adrigamer2950.adriapi.api.files;

import org.bukkit.plugin.Plugin;

/**
 * Class to create configuration files, mainly .yml files
 * @see me.adrigamer2950.adriapi.api.files.yaml.YamlFile
 */
@SuppressWarnings("unused")
public abstract class File
{
    protected final String path;
    protected final String name;
    protected java.io.File file;
    protected final Plugin plugin;
    protected final FileType type;
    public final boolean autoSaveOnServerShutdown;
    public final boolean fileExistsOnPluginResources;

    public File(final String path, final String name, final Plugin plugin, final FileType type, final boolean autoSaveOnServerShutdown, final boolean fileExistsOnPluginResources) {
        this.path = path;
        this.name = name;
        this.plugin = plugin;
        this.type = type;
        this.autoSaveOnServerShutdown = autoSaveOnServerShutdown;
        this.fileExistsOnPluginResources = fileExistsOnPluginResources;
    }

    public enum FileType {
        YAML
    }

    /**
     * Loads the file and its contents
     */
    public abstract void loadFile() throws Throwable;

    /**
     * Saves the content stored into an actual file
     */
    public abstract void saveFile() throws Throwable;

    public final Plugin getPlugin() {
        return this.plugin;
    }

    public final FileType getType() {
        return this.type;
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
