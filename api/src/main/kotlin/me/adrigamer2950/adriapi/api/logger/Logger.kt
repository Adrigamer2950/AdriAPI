package me.adrigamer2950.adriapi.api.logger

import me.adrigamer2950.adriapi.api.logger.builder.LogBuilder
import java.util.logging.Level

@Suppress("unused")
interface Logger {

    var debug: Boolean

    fun log(builder: LogBuilder.() -> Unit)

    fun info(msg: Any) {
        log {
            message = msg
        }
    }

    fun warn(msg: Any, throwable: Throwable? = null) {
        log {
            level = Level.WARNING
            message = msg
            this.throwable = throwable
        }
    }

    fun error(msg: Any, throwable: Throwable? = null) {
        log {
            level = Level.SEVERE
            message = msg
            this.throwable = throwable
        }
    }

    fun debug(msg: Any, throwable: Throwable? = null) {
        log {
            message = msg
            this.throwable = throwable
            debug = true
        }
    }
}