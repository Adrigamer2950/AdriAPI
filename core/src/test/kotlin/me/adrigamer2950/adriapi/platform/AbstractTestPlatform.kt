package me.adrigamer2950.adriapi.platform

import me.adrigamer2950.adriapi.TestPlugin
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform.Companion.plugin
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform.Companion.server
import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestPlan
import org.mockbukkit.mockbukkit.MockBukkit
import org.mockbukkit.mockbukkit.ServerMock

abstract class AbstractTestPlatform {

    companion object {
        lateinit var server: ServerMock
        lateinit var plugin: TestPlugin
    }
}

class GlobalTestListener : TestExecutionListener {

    override fun testPlanExecutionStarted(testPlan: TestPlan?) {
        server = MockBukkit.mock()

        plugin = MockBukkit.load(TestPlugin::class.java)
    }

    override fun testPlanExecutionFinished(testPlan: TestPlan?) {
        MockBukkit.unmock()
    }
}