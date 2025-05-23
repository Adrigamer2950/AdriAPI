package me.adrigamer2950.adriapi.api.logger.impl

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.colors.Colors
import me.adrigamer2950.adriapi.api.logger.Logger
import me.adrigamer2950.adriapi.api.logger.builder.LogBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger as JavaLogger

@Suppress("unused")
class LoggerImpl(name: String, parent: JavaLogger? = Bukkit.getServer().logger) : JavaLogger(name, null), Logger {

    constructor(plugin: APIPlugin, parent: JavaLogger? = Bukkit.getServer().logger) : this(plugin.description.prefix ?: plugin.description.name, parent)

    override var debug: Boolean = false

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

        if (log.debug && !this.debug) return

        if (!log.isMessageInitialized)
            throw IllegalArgumentException("Cannot log empty message")

        val prefix = if (log.debug) "[DEBUG] " else ""
        val message = parseMessage(log.message)

        super.log(log.level, "$prefix$message", log.throwable)
    }

    override fun log(record: LogRecord) {
        record.message = colorizeMessage(record.message)

        super.log(record)
    }
}
