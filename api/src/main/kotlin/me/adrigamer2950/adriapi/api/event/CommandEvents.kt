@file:Suppress("unused")

package me.adrigamer2950.adriapi.api.event

import me.adrigamer2950.adriapi.api.command.Command
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
        return HandlerList()
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
        return HandlerList()
    }
}