package me.devadri.obsidian.platform

import me.devadri.obsidian.TestPlugin
import be.seeseemelk.mockbukkit.ServerMock

abstract class AbstractTestPlatform {

    companion object {
        lateinit var server: ServerMock
        lateinit var plugin: TestPlugin
    }
}