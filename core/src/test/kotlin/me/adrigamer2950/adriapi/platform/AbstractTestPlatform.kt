package me.adrigamer2950.adriapi.platform

import me.adrigamer2950.adriapi.TestPlugin
import be.seeseemelk.mockbukkit.ServerMock

abstract class AbstractTestPlatform {

    companion object {
        lateinit var server: ServerMock
        lateinit var plugin: TestPlugin
    }
}