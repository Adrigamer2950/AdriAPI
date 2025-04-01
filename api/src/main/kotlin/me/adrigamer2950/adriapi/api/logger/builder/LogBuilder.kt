package me.adrigamer2950.adriapi.api.logger.builder

import java.util.logging.Level

class LogBuilder {
    var level: Level = Level.INFO
    var message: Any? = null
    var throwable: Throwable? = null
}