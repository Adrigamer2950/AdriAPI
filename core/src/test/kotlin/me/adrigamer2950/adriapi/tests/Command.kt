package me.adrigamer2950.adriapi.tests

import me.adrigamer2950.adriapi.common.ExampleCommand
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform
import org.junit.jupiter.api.BeforeAll
import kotlin.test.*

class Command : AbstractTestPlatform() {

    companion object {
        @JvmStatic
        @BeforeAll
        fun `Register command`() {
            plugin.commandManager.registerCommand(ExampleCommand(plugin))
        }
    }

    @Test
    fun `Command is registered`() {
        assertNotNull(plugin.commandManager.getCommandOrNull("example"), "Command 'example' should be registered")
    }
}