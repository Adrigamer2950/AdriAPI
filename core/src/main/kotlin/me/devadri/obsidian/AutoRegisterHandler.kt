package me.devadri.obsidian

import me.devadri.obsidian.command.Command
import org.bukkit.event.Listener
import org.jetbrains.annotations.ApiStatus.Internal
import java.lang.reflect.Modifier

@Internal
class AutoRegisterHandler internal constructor(val plugin: ObsidianPlugin) {

    @Throws(IllegalArgumentException::class)
    inline fun <reified T> getInstance(klass: Class<out T>): T {
        val command = if (klass.constructors.any { it.parameters.isEmpty() }) {
            klass.getDeclaredConstructor().newInstance()
        } else if (
            klass.constructors.any {
                it.parameters.size == 1
                        && ObsidianPlugin::class.java.isAssignableFrom(it.parameters[0].type)
            }
        ) {
            klass.getDeclaredConstructor(ObsidianPlugin::class.java).newInstance(plugin)
        } else {
            throw IllegalArgumentException("Class `${klass.simpleName}` must have a no-arg constructor or a constructor of only a APIPlugin (or an implementation of APIPlugin) a object")
        }

        return command
    }

    @Throws(IllegalArgumentException::class)
    inline fun <reified T> registerType(klass: Class<out T>) {
        if (Modifier.isAbstract(klass.modifiers)) {
            throw IllegalArgumentException("Cannot register abstract class `${klass.simpleName}`")
        }

        if (!T::class.java.isAssignableFrom(klass)) {
            throw IllegalArgumentException("Class `${klass.simpleName}` is not a `${T::class.simpleName}`")
        }

        val instance = getInstance(klass)

        if (instance is AutoRegisterFilter) {
            if (!instance.shouldBeRegistered(plugin)) {
                plugin.logger.debug("&6${klass.simpleName} wasn't registered because it didn't pass the filter")
                return
            }
        }

        when (instance) {
            is Listener -> plugin.server.pluginManager.registerEvents(instance, plugin)
            is Command -> plugin.commandManager.registerCommand(instance)
        }
    }
}