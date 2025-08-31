@file:Suppress("unused")

package me.devadri.obsidian.tests

import me.devadri.obsidian.AutoRegister
import me.devadri.obsidian.platform.AbstractTestPlatform
import org.bukkit.event.Listener
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AutoRegister : AbstractTestPlatform() {

    @Test
    fun `Auto-Register Command`() {
        assertNotNull(plugin.commandManager.getCommandOrNull("example_auto"), "Command 'example_auto' should be registered")
    }

    @Test
    fun `Auto-Register Listener`() {
        assertTrue(ExampleListener.isRegistered, "Listener 'ExampleListener' should be registered")
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