package me.devadri.obsidian.logger.builder

import java.util.logging.Level

class LogBuilder {
    var level: Level = Level.INFO
    lateinit var message: Any
    var throwable: Throwable? = null
    var debug: Boolean = false

    val isMessageInitialized get() = this::message.isInitialized
}