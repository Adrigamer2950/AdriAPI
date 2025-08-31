package me.devadri.obsidian.platform

import me.devadri.obsidian.TestPlugin
import me.devadri.obsidian.platform.AbstractTestPlatform.Companion.plugin
import me.devadri.obsidian.platform.AbstractTestPlatform.Companion.server
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