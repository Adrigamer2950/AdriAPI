package me.adrigamer2950.adriapi.api.event.config;

import me.adrigamer2950.adriapi.api.config.Config;
import org.bukkit.plugin.Plugin;

/**
 * This event triggers when a {@link Config} is successfully loaded
 *
 * @see Config
 */
@SuppressWarnings("unused")
public class ConfigLoadedEvent extends ConfigEvent {

    private final Plugin loader;

    public ConfigLoadedEvent(Config config, Plugin loader) {
        super(config);
        this.loader = loader;
    }

    public Plugin getLoader() {
        return loader;
    }
}
