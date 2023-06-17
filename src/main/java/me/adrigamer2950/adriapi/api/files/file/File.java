package me.adrigamer2950.adriapi.api.files.file;

import me.adrigamer2950.adriapi.api.files.file.module.FileModule;
import me.adrigamer2950.adriapi.api.files.file.module.FileYAMLModule;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.IOException;

public final class File {

    public final String path;
    public final String name;
    public final FileType type;
    public java.io.File file;
    public final Plugin plugin;
    private FileModule module;

    public File(String path, String name, FileType type, Plugin plugin) {
        this.path = path;
        this.name = name;
        this.type = type;
        this.plugin = plugin;

        switch (type) {
            case YML: {
                this.module = new FileYAMLModule(this.plugin);
            }
            case JSON: {
                this.module = new FileYAMLModule(this.plugin);
            }
            case TOML: {
                this.module = new FileYAMLModule(this.plugin);
            }
            default: {

            }
        }
    }

    public enum FileType {
        YML(".yml"),
        TOML(".toml"),
        JSON(".json");

        public String name;

        FileType(String s) {
            this.name = s;
        }

        public String getName() {
            return this.name;
        }
    }

    public void createFile() throws IOException {
        java.io.File path = new java.io.File(this.path);
        if(!path.exists())
            if(!path.mkdir())
                if(!path.mkdirs())
                    throw new IOException("Plugin config folder couldn't be created. Are you sure you have given to the server the enough permissions to create folders?");

        java.io.File file = new java.io.File(this.path, this.name + this.type.getName());

        if(!file.exists())
            if(!file.createNewFile())
                throw new IOException("File couldn't be created. Are you sure you have given to the server the enough permissions to create files?");

        this.module.loadConfiguration(file);

        this.file = file;
    }

    public boolean fileExists() {
        return this.file.exists();
    }

    public Object get(String path) {
        return this.module.get(path);
    }

    public void save() throws IOException {
        java.io.File file = new java.io.File(this.path, this.name + this.type.getName());

        this.module.saveFile(file);
    }
}
