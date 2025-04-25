package me.adrigamer2950.adriapi.platform

import me.adrigamer2950.adriapi.TestPlugin
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

abstract class AbstractTestPlatform {

    lateinit var server: ServerMock
    lateinit var plugin: TestPlugin

    @BeforeTest
    open fun startServer() {
        server = MockBukkit.mock()

        plugin = MockBukkit.load(TestPlugin::class.java)
    }

    @AfterTest
    open fun stopServer() {
        MockBukkit.unmock()
    }
}