package me.adrigamer2950.adriapi.api.logger;

import me.adrigamer2950.adriapi.api.colors.Colors;
import org.bukkit.Bukkit;

/**
 * Main Logger class
 */
public class APILogger {

    private final String name;
    private final APILogger parent;

    public APILogger(String name, APILogger parent) {
        this.name = name;
        this.parent = parent;
    }

    /**
     * Send a message to console with colored text
     * @param message The message to be sent
     */
    public void log(String message) {
        Bukkit.getConsoleSender().sendMessage(Colors.translateColors( Colors.translateAPIColors(
                String.format("&r[%s&r] %s",
                        parent == null ? name : parent.name + " - " + name,
                        message
                ) )));
    }
}
