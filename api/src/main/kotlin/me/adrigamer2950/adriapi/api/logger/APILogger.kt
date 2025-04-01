package me.adrigamer2950.adriapi.api.logger

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.colors.Colors
import me.adrigamer2950.adriapi.api.logger.builder.LogBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

/**
 * Main Logger class
 */
@Suppress("unused")
class APILogger(name: String, parent: Logger? = Bukkit.getServer().logger) : Logger(name, null), me.adrigamer2950.adriapi.api.logger.Logger {

    constructor(plugin: APIPlugin, parent: Logger? = Bukkit.getServer().logger) : this(plugin.description.prefix ?: plugin.description.name, parent)

    var debug: Boolean = false

    init {
        setParent(parent)
        setLevel(Level.ALL)
    }

    private fun colorizeMessage(msg: String): String {
        return Colors.translateToAnsi(
            Colors.translateAPIColors(
                msg
            ),
            '§'
        )
    }

    private fun parseMessage(msg: Any): String {
        return if (msg is Component) {
            LegacyComponentSerializer.legacyAmpersand().serialize(msg)
        } else {
            msg.toString()
        }
    }

    override fun log(builder: LogBuilder.() -> Unit) {
        val log = LogBuilder().apply(builder)

        if (log.message == null) {
            throw IllegalArgumentException("Cannot log null message")
        }

        super<Logger>.log(log.level, parseMessage(log.message!!), log.throwable)
    }

    override fun log(record: LogRecord) {
        record.message = colorizeMessage(record.message)

        super<Logger>.log(record)
    }
}
