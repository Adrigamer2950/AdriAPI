package me.adrigamer2950.adriapi.api.logger

import me.adrigamer2950.adriapi.api.APIPlugin
import me.adrigamer2950.adriapi.api.colors.Colors
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer

import java.util.logging.Level
import java.util.logging.LogRecord
import java.util.logging.Logger

/**
 * Main Logger class
 */
@Suppress("unused")
@SuppressWarnings("unused")
class APILogger(name: String, parent: Logger) : Logger(name, null) {

    constructor(plugin: APIPlugin, parent: Logger) : this(plugin.description.prefix ?: plugin.description.name, parent)
    constructor(plugin: APIPlugin) : this(plugin, plugin.server.logger)

    var debug: Boolean = false

    init {
        setParent(parent)
        setLevel(Level.ALL)
    }

    fun colorizeMessage(msg: String): String {
        return Colors.translateToAnsi(
            Colors.translateAPIColors(
                msg
            ),
            '§'
        )
    }

    /**
     * @param component The component
     */
    fun info(component: Component) {
        this.info(LegacyComponentSerializer.legacyAmpersand().serialize(component))
    }

    /**
     * @param msg The message that you want to send
     */
    override fun info(msg: String) {
        super.info(msg)
    }

    /**
     * @param component The component
     */
    fun warn(component: Component) {
        this.warn(LegacyComponentSerializer.legacyAmpersand().serialize(component))
    }

    /**
     * @param msg The message that you want to send
     */
    fun warn(msg: String) {
        super.warning(msg)
    }

    /**
     * @param component The component
     */
    fun error(component: Component) {
        this.error(LegacyComponentSerializer.legacyAmpersand().serialize(component))
    }

    /**
     * @param msg The message that you want to send
     */
    fun error(msg: String) {
        super.severe(msg)
    }

    /**
     * @param throwable The throwable that you want to log
     */
    fun error(throwable: Throwable) {
        this.error("An error occurred: " + throwable.message, throwable)
    }

    /**
     * @param component The component
     * @param throwable The throwable that you want to log
     */
    fun error(component: Component, throwable: Throwable) {
        this.error(LegacyComponentSerializer.legacyAmpersand().serialize(component), throwable)
    }

    /**
     * @param msg The message that you want to send
     * @param throwable The throwable that you want to log
     */
    fun error(msg: String, throwable: Throwable) {
        super.log(Level.SEVERE, msg, throwable)
    }

    /**
     * @param component The component
     */
    fun debug(component: Component) {
        this.debug(component, false)
    }

    /**
     * @param component The component
     * @param forceLog  If the log should be forced
     */
    fun debug(component: Component, forceLog: Boolean) {
        if (!this.debug && !forceLog) return

        this.info(Component.text("[DEBUG]").append(component))
    }

    /**
     * @param msg The message that you want to send
     */
    fun debug(msg: String) {
        this.debug(msg, false)
    }

    /**
     * @param msg The message that you want to send
     * @param forceLog  If the log should be forced
     */
    fun debug(msg: String, forceLog: Boolean) {
        if (!this.debug && !forceLog) return

        super.info("[DEBUG] $msg")
    }

    /**
     * @param level     The level of the log
     * @param component The component
     */
    fun log(level: Level, component: Component) {
        this.log(level, LegacyComponentSerializer.legacyAmpersand().serialize(component))
    }

    /**
     * @param level The level of the log
     * @param msg   The message that you want to send
     */
    override fun log(level: Level, msg: String) {
        super.log(level, colorizeMessage(msg))
    }

    @Override
    override fun log(logRecord: LogRecord) {
        logRecord.message = colorizeMessage(logRecord.message)
        super.log(logRecord)
    }
}
