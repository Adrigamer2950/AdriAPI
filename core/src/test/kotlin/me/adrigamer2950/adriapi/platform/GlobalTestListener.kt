package me.adrigamer2950.adriapi.platform

import me.adrigamer2950.adriapi.TestPlugin
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform.Companion.plugin
import me.adrigamer2950.adriapi.platform.AbstractTestPlatform.Companion.server
import org.junit.platform.launcher.TestExecutionListener
import org.junit.platform.launcher.TestPlan
import be.seeseemelk.mockbukkit.MockBukkit

class GlobalTestListener : TestExecutionListener {

    override fun testPlanExecutionStarted(testPlan: TestPlan?) {
        server = MockBukkit.mock()

        plugin = MockBukkit.load(TestPlugin::class.java)
    }

    override fun testPlanExecutionFinished(testPlan: TestPlan?) {
        MockBukkit.unmock()
    }
}