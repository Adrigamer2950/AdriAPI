package me.adrigamer2950.adriapi.api.logger;

import lombok.Getter;
import lombok.NonNull;
import me.adrigamer2950.adriapi.api.colors.Colors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main Logger class
 */
@SuppressWarnings("unused")
@Getter
public class APILogger {

    @NonNull
    private final Logger logger;

    public APILogger(@NonNull String name, @Nullable Logger logger) {
        this.logger = logger == null ? Logger.getLogger(name) : logger;
    }

    private String colorizeMessage(String msg) {
        return Colors.translateColors(
                Colors.translateAPIColors(
                        msg
                ),
                '§',
                true
        );
    }

    /**
     * @param component The component
     */
    public void info(Component component) {
        this.info(LegacyComponentSerializer.legacyAmpersand().serialize(component));
    }

    /**
     * @param msg The message that you want to send
     * @see APILogger#info(Component)
     */
    public void info(String msg) {
        this.getLogger().info(colorizeMessage(msg));
    }

    /**
     * @param component The component
     */
    public void warn(Component component) {
        this.warn(LegacyComponentSerializer.legacyAmpersand().serialize(component));
    }

    /**
     * @param msg The message that you want to send
     * @see APILogger#warn(Component)
     */
    public void warn(String msg) {
        this.getLogger().warning(colorizeMessage(msg));
    }

    /**
     * @param component The component
     */
    public void error(Component component) {
        this.error(LegacyComponentSerializer.legacyAmpersand().serialize(component));
    }

    /**
     * @param msg The message that you want to send
     * @see APILogger#error(Component)
     */
    public void error(String msg) {
        this.getLogger().severe(colorizeMessage(msg));
    }

    /**
     * @param component The component
     */
    public void debug(Component component) {
        this.info(Component.text("[DEBUG]").append(component));
    }

    /**
     * @param msg The message that you want to send
     * @see APILogger#debug(Component)
     */
    public void debug(String msg) {
        this.info("[DEBUG] %s".formatted(msg));
    }

    /**
     * @param component The component
     */
    public void log(Level level, Component component) {
        this.log(level, LegacyComponentSerializer.legacyAmpersand().serialize(component));
    }

    /**
     * @param level The level of the log
     * @param msg   The message that you want to send
     * @see APILogger#log(Level, Component)
     */
    public void log(Level level, String msg) {
        this.getLogger().log(level, colorizeMessage(msg));
    }
}
