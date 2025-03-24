package me.adrigamer2950.adriapi.api.command.manager

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.command.Command
import org.bukkit.Bukkit

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles
import java.lang.invoke.MethodType
import java.util.Optional

/**
 * Register and unregister commands
 *
 * @see Command
 * @since 1.0.0
 */
@Suppress("unused")
class CommandManager(val plugin: APIPlugin) {

    val syncCommands: MethodHandle? = this.findSyncCommandsMethod()

    fun findSyncCommandsMethod(): MethodHandle {
        try {
            return MethodHandles.lookup().findVirtual(
                Bukkit.getServer().javaClass, "syncCommands", MethodType.methodType(
                    Void::class.java
                )
            )
        } catch (e: Exception) {
            throw RuntimeException("Could not find syncCommands method", e)
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
    }

    fun unRegisterCommand(command: Command) {
        command.unRegister()
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
            .filter { it.commandName == name }
            .findFirst()
    }

    fun getCommandOrNull(name: String): Command {
        return this.getCommand(name).orElse(null)
    }
}
