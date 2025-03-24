package me.adrigamer2950.adriapi.api.util

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.command.manager.CommandManager
import org.bukkit.Bukkit
import org.bukkit.command.Command

object CommandUtil {

    fun unRegisterCommand(command: Command, commandManager: CommandManager) {
        val commandMap = Bukkit.getCommandMap()

        commandMap.knownCommands.remove(command.name)
        command.aliases.forEach { commandMap.knownCommands.remove(it) }
        command.unregister(commandMap)

        commandManager.syncCommands()
    }

    fun registerCommand(command: Command, plugin: APIPlugin) {
        val commandMap = Bukkit.getCommandMap()

        val foundCommand = commandMap.getCommand(command.name)

        if (foundCommand != null) unRegisterCommand(foundCommand, plugin.commandManager)

        commandMap.register(plugin.name, command)
    }
}
