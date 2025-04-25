package me.adrigamer2950.adriapi.tests

import me.adrigamer2950.adriapi.common.ExampleCommand
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform
import kotlin.test.Test
import kotlin.test.assertNotNull

class Command : AbstractTestPlatform() {

    @Test
    fun `Command is registered`() {
        plugin.commandManager.registerCommand(ExampleCommand(plugin))

        assertNotNull(plugin.commandManager.getCommandOrNull("example"), "Command 'example' should be registered")
    }
}