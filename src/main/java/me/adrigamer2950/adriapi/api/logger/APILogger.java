package me.adrigamer2950.adriapi.api.logger;

import lombok.Getter;
import lombok.NonNull;
import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.colors.Colors;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * Main Logger class
 */
@SuppressWarnings("unused")
@Getter
public class APILogger extends Logger {

    /**
     * @param plugin The plugin
     */
    public APILogger(@NonNull APIPlugin plugin) {
        //noinspection UnstableApiUsage
        this(plugin, plugin.getServer().getLogger());
    }

    /**
     * @param plugin The plugin
     * @param parent The logger's parent
     */
    public APILogger(@NonNull APIPlugin plugin, @NonNull @NotNull Logger parent) {
        //noinspection deprecation
        this(
                plugin.getDescription().getPrefix() != null ? plugin.getDescription().getPrefix() : plugin.getDescription().getName(),
                parent
        );
    }

    /**
     * @param name   The logger's name
     * @param parent The logger's parent
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    @Deprecated(forRemoval = true)
    public APILogger(@NonNull @NotNull String name, @NonNull @NotNull Logger parent) {
        super(name, null);

        setParent(parent);
        setLevel(Level.ALL);
    }

    private String colorizeMessage(String msg) {
        return Colors.translateToAnsi(
                Colors.translateAPIColors(
                        msg
                ),
                '§'
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
        super.info(msg);
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
        super.warning(msg);
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
        super.severe(msg);
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
        super.info("[DEBUG] %s".formatted(msg));
    }

    /**
     * @param level     The level of the log
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
        super.log(level, colorizeMessage(msg));
    }

    @Override
    public void log(@NotNull LogRecord logRecord) {
        logRecord.setMessage(
                colorizeMessage(logRecord.getMessage())
        );
        super.log(logRecord);
    }
}
