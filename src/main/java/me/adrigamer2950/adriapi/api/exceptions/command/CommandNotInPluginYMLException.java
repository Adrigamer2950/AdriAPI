package me.adrigamer2950.adriapi.api.exceptions.command;

/**
 * Thrown if trying to register a command that isn't registered in your plugin.yml
 */
public class CommandNotInPluginYMLException extends RuntimeException {

    public CommandNotInPluginYMLException(String message) {
        super(message);
    }
}
