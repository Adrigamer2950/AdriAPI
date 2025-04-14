package me.adrigamer2950.adriapi.api

import me.adrigamer2950.adriapi.api.command.Command
import me.adrigamer2950.adriapi.api.command.manager.CommandManager
import me.adrigamer2950.adriapi.api.listeners.InventoriesListener
import me.adrigamer2950.adriapi.api.library.manager.LibraryManager
import me.adrigamer2950.adriapi.api.logger.impl.LoggerImpl
import me.adrigamer2950.adriapi.api.logger.Logger
import me.adrigamer2950.adriapi.api.scheduler.Scheduler
import me.adrigamer2950.adriapi.api.util.ServerType
import me.adrigamer2950.adriapi.api.util.bStats
import org.bukkit.event.Listener
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import org.jetbrains.annotations.ApiStatus
import java.io.File

/**
 * Extension of JavaPlugin which works as
 * a main class for plugins, just like JavaPlugin
 *
 * @see org.bukkit.plugin.java.JavaPlugin
 * @since 2.0.0
 */
@Suppress("unused")
abstract class APIPlugin : JavaPlugin {

    constructor() : super()

    /**
     * Deprecated constructor. Will be removed when JavaPlugin's equivalent is removed
     */
    @Deprecated("Will be removed when JavaPlugin's equivalent is removed")
    constructor(loader: JavaPluginLoader, description: PluginDescriptionFile, dataFolder: File, file: File) : super(
        loader,
        description,
        dataFolder,
        file
    )

    open val logger: Logger = LoggerImpl(this)

    /**
     * Command Manager. Used to register a Command
     */
    lateinit var commandManager: CommandManager
        protected set

    /**
     * Custom scheduler that takes advantage of Folia's scheduler
     * without the need to check all the time if the server is running Folia
     */
    lateinit var scheduler: Scheduler
        protected set

    /**
     * bStats wrapper
     */
    lateinit var bStats: bStats
        protected set

    protected open fun bStatsServiceId(): Int = 0

    /**
     * The library manager. Used to download libraries on runtime
     */
    lateinit var libraryManager: LibraryManager
        protected set

    var debug: Boolean
        get() = logger.debug
        set(debug) {
            logger.debug = debug
        }

    final override fun onLoad() {
        this.libraryManager = LibraryManager.get(this)

        this.onPreLoad()
    }

    final override fun onEnable() {
        logger.debug("&6Loading hooks...")
        loadHooks()

        this.registerListener(InventoriesListener(this))

        onPostLoad()
    }

    final override fun onDisable() {
        logger.debug("&6Disabling plugin...")
        onUnload()

        if (this::bStats.isInitialized)
            bStats.shutdown()
    }

    private fun loadHooks() {
        logger.debug("&6Loading Command Manager...")
        commandManager = CommandManager(this)

        logger.debug("&6Loading Scheduler...")
        scheduler = Scheduler.make(this, ServerType.type == ServerType.FOLIA)

        if (bStatsServiceId() != 0) {
            logger.debug("&6Loading bStats hook...")
            bStats = bStats(this, bStatsServiceId())
        }
    }

    /**
     * Called before hooks are loaded
     */
    abstract fun onPreLoad()

    /**
     * Called after hooks are loaded
     */
    abstract fun onPostLoad()

    /**
     * Called when disabling the plugin
     */
    abstract fun onUnload()

    /**
     * Registers a [Command]
     *
     * @param commands The commands
     * @see Command
     */
    protected open fun registerCommand(vararg commands: Command) {
        commands.forEach {
            commandManager.registerCommand(it)
        }
    }

    /**
     * Unregisters a [Command] by its name
     *
     * @param name The command
     * @see Command
     */
    protected open fun unRegisterCommand(name: String) {
        this.commandManager.getCommand(name)
            .ifPresent {
                this.unRegisterCommand(it)
            }
    }

    /**
     * Unregisters a [Command]
     *
     * @param command The command
     * @see Command
     */
    protected open fun unRegisterCommand(command: Command) {
        this.commandManager.unRegisterCommand(command)
    }

    /**
     * Registers a [Listener]
     *
     * @param listeners The listener
     * @see Listener
     */
    protected open fun registerListener(vararg listeners: Listener) {
        listeners.forEach {
            server.pluginManager.registerEvents(it, this)
        }
    }

    /**
     * @return The plugin's Logger
     * @see APIPlugin.getLogger
     */
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    @Deprecated("In favor of APIPlugin#getLogger()", ReplaceWith("logger"))
    fun getApiLogger(): Logger {
        return logger
    }
}