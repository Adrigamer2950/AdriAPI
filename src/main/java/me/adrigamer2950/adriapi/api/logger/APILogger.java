package me.adrigamer2950.adriapi.api.logger;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
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
@Setter
@Getter
public class APILogger extends Logger {

    private boolean debug;

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
    public void info(@NonNull Component component) {
        this.info(LegacyComponentSerializer.legacyAmpersand().serialize(component));
    }

    /**
     * @param msg The message that you want to send
     */
    public void info(@NonNull String msg) {
        super.info(msg);
    }

    /**
     * @param component The component
     */
    public void warn(@NonNull Component component) {
        this.warn(LegacyComponentSerializer.legacyAmpersand().serialize(component));
    }

    /**
     * @param msg The message that you want to send
     */
    public void warn(@NonNull String msg) {
        super.warning(msg);
    }

    /**
     * @param component The component
     */
    public void error(@NonNull Component component) {
        this.error(LegacyComponentSerializer.legacyAmpersand().serialize(component));
    }

    /**
     * @param msg The message that you want to send
     */
    public void error(@NonNull String msg) {
        super.severe(msg);
    }

    /**
     * @param throwable The throwable that you want to log
     */
    public void error(@NonNull Throwable throwable) {
        this.error("An error occurred: " + throwable.getMessage(), throwable);
    }

    /**
     * @param component The component
     * @param throwable The throwable that you want to log
     */
    public void error(@NonNull Component component, @NonNull Throwable throwable) {
        this.error(LegacyComponentSerializer.legacyAmpersand().serialize(component), throwable);
    }

    /**
     * @param msg The message that you want to send
     * @param throwable The throwable that you want to log
     */
    public void error(@NonNull String msg, @NonNull Throwable throwable) {
        super.log(Level.SEVERE, msg, throwable);
    }

    /**
     * @param component The component
     */
    public void debug(@NonNull Component component) {
        this.debug(component, false);
    }

    /**
     * @param component The component
     * @param forceLog  If the log should be forced
     */
    public void debug(@NonNull Component component, boolean forceLog) {
        if (!this.isDebug() && !forceLog) return;

        this.info(Component.text("[DEBUG]").append(component));
    }

    /**
     * @param msg The message that you want to send
     */
    public void debug(@NonNull String msg) {
        this.debug(msg, false);
    }

    /**
     * @param msg The message that you want to send
     * @param forceLog  If the log should be forced
     */
    public void debug(@NonNull String msg, boolean forceLog) {
        if (!this.isDebug() && !forceLog) return;

        super.info("[DEBUG] %s".formatted(msg));
    }

    /**
     * @param level     The level of the log
     * @param component The component
     */
    public void log(@NonNull Level level, @NonNull Component component) {
        this.log(level, LegacyComponentSerializer.legacyAmpersand().serialize(component));
    }

    /**
     * @param level The level of the log
     * @param msg   The message that you want to send
     */
    public void log(@NonNull Level level, @NonNull String msg) {
        super.log(level, colorizeMessage(msg));
    }

    @Override
    public void log(@NotNull @NonNull LogRecord logRecord) {
        logRecord.setMessage(
                colorizeMessage(logRecord.getMessage())
        );
        super.log(logRecord);
    }
}
