package me.adrigamer2950.adriapi.api.util

import me.adrigamer2950.adriapi.api.APIPlugin
import org.bukkit.Bukkit
import org.bukkit.command.Command

object CommandUtil {

    @JvmStatic
    fun unRegisterCommand(command: Command, plugin: APIPlugin) {
        val commandMap = Bukkit.getCommandMap()

        listOf<String>(command.name, *command.aliases.toTypedArray()).forEach {
            commandMap.knownCommands.remove(it)
            commandMap.knownCommands.remove("${plugin.name.lowercase()}:$it")
        }

        plugin.commandManager.syncCommands()
    }

    @JvmStatic
    fun registerCommand(command: Command, plugin: APIPlugin) {
        val commandMap = Bukkit.getCommandMap()

        listOf<String>(command.name, *command.aliases.toTypedArray()).forEach {
            commandMap.knownCommands[it] = command
            commandMap.knownCommands["${plugin.name.lowercase()}:$it"] = command
        }

        plugin.commandManager.syncCommands()
    }
}
