package me.devadri.obsidian.logger.impl

import me.devadri.obsidian.ObsidianPlugin
import me.devadri.obsidian.logger.Logger
import me.devadri.obsidian.logger.builder.LogBuilder
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import java.util.logging.Level
import java.util.logging.Logger as JavaLogger

@Suppress("unused")
class LoggerImpl(name: String, parent: JavaLogger? = Bukkit.getServer().logger) : JavaLogger(name, null), Logger {

    constructor(plugin: ObsidianPlugin, parent: JavaLogger? = Bukkit.getServer().logger) : this(plugin.description.prefix ?: plugin.description.name, parent)

    override var debug: Boolean = false

    init {
        setParent(parent)
        setLevel(Level.ALL)
    }

    private fun parseMessage(msg: Any): String {
        return if (msg is Component) {
            PlainTextComponentSerializer.plainText().serialize(msg)
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
}
