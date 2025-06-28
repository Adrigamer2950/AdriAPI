package me.adrigamer2950.adriapi.api.command.manager

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.command.Command
import me.adrigamer2950.adriapi.api.util.ClassUtil
import org.bukkit.Bukkit
import java.lang.reflect.Method
import java.util.*

/**
 * Register and unregister commands
 *
 * @see Command
 * @since 1.0.0
 */
@Suppress("unused")
class CommandManager(val plugin: APIPlugin) {

    private val syncCommands: Method? = this.findSyncCommandsMethod()

    private val _commands = mutableListOf<Command>()

    val commands: List<Command>
        get() = this._commands.toList()

    private fun findSyncCommandsMethod(): Method? {
        // Ignore syncCommands method if plugin is running as a MockBukkit (unit testing framework) test
        if (ClassUtil.classExists("org.mockbukkit.mockbukkit.MockBukkit"))
            return null

        return try {
            val method = Bukkit.getServer().javaClass.getDeclaredMethod("syncCommands")
            method.isAccessible = true

            method
        } catch (e: Exception) {
            plugin.logger.error("Could not find syncCommands method", e)

            null
        }
    }

    /**
     * Register commands
     *
     * @param command The command that you want to register
     * @throws IllegalArgumentException if command is already registered
     */
    fun registerCommand(command: Command) {
        command.register()

        _commands.add(command)
    }

    fun unRegisterCommand(command: Command) {
        command.unRegister()

        _commands.remove(command)
    }

    fun syncCommands() {
        try {
            this.syncCommands?.invoke(Bukkit.getServer())
        } catch (e: Throwable) {
            this.plugin.logger.error("Could not sync commands", e)
        }
    }

    fun getCommand(name: String): Optional<Command> {
        return Bukkit.getCommandMap().knownCommands.values.stream()
            .filter { it is Command }
            .map { it as Command }
            .filter { it.plugin == this.plugin }
            .filter { it.info.name == name }
            .findFirst()
    }

    fun getCommandOrNull(name: String): Command? {
        return this.getCommand(name).orElse(null)
    }
}
