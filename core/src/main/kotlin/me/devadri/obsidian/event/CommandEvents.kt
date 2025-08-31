@file:Suppress("unused")

package me.devadri.obsidian.event

import me.devadri.obsidian.command.Command
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class CommandEvent(val command: Command) : Event()

class CommandLoadedEvent(command: Command) : CommandEvent(command) {

    companion object {
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }

    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }
}

class CommandUnloadedEvent(command: Command) : CommandEvent(command) {

    companion object {
        private val handlers = HandlerList()

        @JvmStatic
        fun getHandlerList(): HandlerList {
            return handlers
        }
    }

    override fun getHandlers(): HandlerList {
        return Companion.handlers
    }
}