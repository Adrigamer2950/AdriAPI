@file:Suppress("unused")
@file:OptIn(ExperimentalAPI::class)

package me.adrigamer2950.adriapi.tests

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.AutoRegister
import me.adrigamer2950.adriapi.api.ExperimentalAPI
import me.adrigamer2950.adriapi.api.command.AbstractCommand
import me.adrigamer2950.adriapi.api.user.User
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform
import org.bukkit.event.Listener
import kotlin.test.Test
import kotlin.test.assertTrue

class AutoRegister : AbstractTestPlatform() {

    @Test
    fun `Auto-Register Command`() {
        assertTrue(plugin.commandManager.getCommandOrNull("example") != null, "Command 'example' should be registered")
    }

    @Test
    fun `Auto-Register Listener`() {
        assertTrue(ExampleListener.isRegistered, "Listener 'ExampleListener' should be registered")
    }
}

@AutoRegister
class ExampleCommand(plugin: APIPlugin) : AbstractCommand(plugin, "example") {

    override fun execute(user: User, args: Array<out String>, commandName: String) {
        user.sendMessage("nice command")
    }
}

@AutoRegister
class ExampleListener : Listener {

    companion object {
        var isRegistered = false
            private set
    }

    init {
        isRegistered = true
    }
}