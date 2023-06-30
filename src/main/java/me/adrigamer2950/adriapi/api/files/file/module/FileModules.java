package me.adrigamer2950.adriapi.api.files.file.module;

import me.adrigamer2950.adriapi.api.files.file.File;
import me.adrigamer2950.adriapi.api.files.file.module.yaml.FileYAMLModule;
import org.bukkit.plugin.Plugin;

public class FileModules {

    public static FileModule createNewModule(Plugin plugin, File.FileType type) {
        switch (type) {
            case YML: {
                return new FileYAMLModule(plugin);
            }
            case JSON: {

            }
            case TOML: {

            }
        }

        return null;
    }
}
