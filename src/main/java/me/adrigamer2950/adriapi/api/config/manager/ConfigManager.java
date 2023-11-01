package me.adrigamer2950.adriapi.api.config.manager;

import me.adrigamer2950.adriapi.api.config.yaml.YamlConfig;
import me.adrigamer2950.adriapi.api.exceptions.DuplicatedManagerException;
import me.adrigamer2950.adriapi.api.config.Config;
import org.apache.commons.lang.Validate;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.ApiStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Used to register and get easily configuration files, for example:
 * {@code FileManager.getManager(Bukkit.getPluginManager().getPlugin("MyPlugin")).getConfig("config"); }
 * would grant you access to that configuration file in order to edit its contents
 */
@SuppressWarnings("unused")
public class ConfigManager {
    @ApiStatus.Internal
    public static final List<ConfigManager> CONFIG_MANAGERS = new ArrayList<>();
    private final Plugin plugin;
    private final List<Config> configs;

    public static ConfigManager getManager(Plugin plugin) {
        for (final ConfigManager fM : ConfigManager.CONFIG_MANAGERS) {
            if (fM.getPlugin().equals(plugin)) {
                return fM;
            }
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    public ConfigManager(Plugin plugin) {
        this.configs = new ArrayList<>();

        if (getManager(plugin) != null) {
            throw new DuplicatedManagerException(String.format("ConfigManager for plugin %s v%s has already been created and cannot be duplicated", plugin.getName(), plugin.getDescription().getVersion()));
        }

        this.plugin = plugin;

        CONFIG_MANAGERS.add(this);
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public void registerConfigFile(Config f) {
        Validate.notNull(f, "File must not be null!");

        this.configs.add(f);
    }

    public void createConfigFiles() {
        for (Config f : this.configs) {
            try {
                f.loadConfig();
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveConfigFiles() {
        for (Config f : this.configs)
            if (f.autoSaveOnServerShutdown) {
                try {
                    f.saveConfig();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
    }

    public YamlConfig getConfig(String name) {
        for (Config f : this.configs) {
            if (!(f instanceof YamlConfig)) continue;
            if (Objects.equals(f.getName(), name)) return (YamlConfig) f;
        }

        return null;
    }
}