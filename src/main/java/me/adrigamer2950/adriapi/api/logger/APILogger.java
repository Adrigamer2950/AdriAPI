package me.adrigamer2950.adriapi.api.logger;

import lombok.Getter;
import me.adrigamer2950.adriapi.api.colors.Colors;

import java.util.logging.Logger;

/**
 * Main Logger class
 */
@Getter
public class APILogger {

    private final Logger logger;

    public APILogger(String name, Logger logger) {
        this.logger = logger == null ? Logger.getLogger(name) : logger;
    }

    private String colorizeMessage(String msg) {
        return Colors.translateColors(
                Colors.translateAPIColors(
                        msg
                )
        );
    }

    public void info(String msg) {
        this.getLogger().info(colorizeMessage(msg));
    }

    public void warn(String msg) {
        this.getLogger().warning(colorizeMessage(msg));
    }

    public void error(String msg) {
        this.getLogger().severe(colorizeMessage(msg));
    }

    public void debug(String msg) {
        this.log(Level.DEBUG, msg);
    }

    public void log(Level level, String msg) {
        this.getLogger().log(level, colorizeMessage(msg));
    }

    public static class Level extends java.util.logging.Level {

        public static final Level DEBUG = new Level("DEBUG", 950);

        protected Level(String name, int value) {
            super(name, value);
        }
    }
}
