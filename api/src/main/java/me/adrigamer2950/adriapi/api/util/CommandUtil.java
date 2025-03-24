package me.adrigamer2950.adriapi.api.util;

import me.adrigamer2950.adriapi.api.APIPlugin;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;

public class CommandUtil {

    public static void unRegisterCommand(Command command, CommandManager commandManager) {
        CommandMap commandMap = Bukkit.getCommandMap();

        commandMap.getKnownCommands().remove(command.getName());
        command.getAliases().forEach(a -> commandMap.getKnownCommands().remove(a));
        command.unregister(commandMap);

        commandManager.syncCommands();
    }

    public static void registerCommand(Command command, APIPlugin plugin) {
        CommandMap commandMap = Bukkit.getCommandMap();

        org.bukkit.command.Command foundCommand = commandMap.getCommand(command.getName());

        if (foundCommand != null) CommandUtil.unRegisterCommand(foundCommand, plugin.getCommandManager());

        commandMap.register(plugin.getName(), command);
    }
}
