package me.adrigamer2950.adriapi.api.util

import me.adrigamer2950.adriapi.api.APIPlugin
import org.bukkit.Bukkit
import org.bukkit.command.Command

object CommandUtil {

    @JvmOverloads
    @JvmStatic
    fun unRegisterCommand(command: Command, plugin: APIPlugin, isPrefixCommand: Boolean = false) {
        val commandMap = Bukkit.getCommandMap()

        commandMap.knownCommands.remove("${
            if (isPrefixCommand) "${plugin.name.lowercase()}:" else ""
        }${command.name}")
        command.aliases.forEach { commandMap.knownCommands.remove(it) }
        command.unregister(commandMap)

        if (!isPrefixCommand) {
            commandMap.knownCommands.filter { it.key.startsWith("${plugin.name.lowercase()}:") }.forEach { this.unRegisterCommand(it.value, plugin, true) }
        }

        plugin.commandManager.syncCommands()
    }

    @JvmStatic
    fun registerCommand(command: Command, plugin: APIPlugin) {
        val commandMap = Bukkit.getCommandMap()

        val foundCommand = commandMap.getCommand(command.name)

        if (foundCommand != null) unRegisterCommand(foundCommand, plugin)

        commandMap.register(plugin.name, command)

        plugin.commandManager.syncCommands()
    }
}
