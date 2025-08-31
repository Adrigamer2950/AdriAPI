package me.devadri.obsidian.tests

import me.devadri.obsidian.toUser
import me.devadri.obsidian.common.ExampleCommand
import me.devadri.obsidian.platform.AbstractTestPlatform
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

    @Test
    fun `Command Tab-Complete`() {
        val command = plugin.commandManager.getCommandOrNull("example")!!

        val player = server.addPlayer()
        val user = player.toUser()

        val result = command.tabComplete(user, arrayOf("test1", "test2"), command.info.name)

        assertTrue(result.isNotEmpty(), "Tab-complete should not be empty")
        assertTrue(result.size == 2, "Tab-complete array should have only 2 elements")
        assertEquals("test3", result[0], "First element should be 'test2'")
        assertEquals("test4", result[1], "Second element should be 'test3'")
    }
}