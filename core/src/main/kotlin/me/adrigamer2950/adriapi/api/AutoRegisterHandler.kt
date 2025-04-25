package me.adrigamer2950.adriapi.api

import me.adrigamer2950.adriapi.api.command.Command
import org.bukkit.event.Listener
import org.jetbrains.annotations.ApiStatus.Internal
import java.lang.reflect.Modifier

@Internal
class AutoRegisterHandler internal constructor(val plugin: APIPlugin) {

    @Throws(IllegalArgumentException::class)
    inline fun <reified T> getInstance(klass: Class<out T>): T {
        val command = if (klass.constructors.any { it.parameters.isEmpty() }) {
            klass.getDeclaredConstructor().newInstance() as T
        } else if (
            klass.constructors.any {
                it.parameters.size == 1
                        && APIPlugin::class.java.isAssignableFrom(it.parameters[0].type)
            }
        ) {
            klass.constructors.firstOrNull {
                it.parameters.size == 1
                        && APIPlugin::class.java.isAssignableFrom(it.parameters[0].type)
            }?.newInstance(plugin) as T
        } else {
            throw IllegalArgumentException("Class `${klass.simpleName}` must have a no-arg constructor or a constructor of only a APIPlugin (or an implementation of APIPlugin) a object")
        }

        return command
    }

    @OptIn(ExperimentalAPI::class)
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