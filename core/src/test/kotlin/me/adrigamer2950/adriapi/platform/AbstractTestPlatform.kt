package me.adrigamer2950.adriapi.platform

import me.adrigamer2950.adriapi.TestPlugin
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock

abstract class AbstractTestPlatform {

    companion object {
        lateinit var server: ServerMock
        lateinit var plugin: TestPlugin

        @JvmStatic
        @BeforeAll
        fun startServer() {
            server = MockBukkit.mock()

            plugin = MockBukkit.load(TestPlugin::class.java)
        }

        @JvmStatic
        @AfterAll
        fun stopServer() {
            MockBukkit.unmock()
        }
    }
}