package me.adrigamer2950.adriapi.api

import me.adrigamer2950.adriapi.api.command.Command
import me.adrigamer2950.adriapi.api.command.manager.CommandManager
import me.adrigamer2950.adriapi.api.library.manager.LibraryManager
import me.adrigamer2950.adriapi.api.logger.APILogger
import me.adrigamer2950.adriapi.api.scheduler.Scheduler
import me.adrigamer2950.adriapi.api.util.ServerType
import me.adrigamer2950.adriapi.api.util.bStats
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.ApiStatus
import java.util.function.Consumer

/**
 * Extension of JavaPlugin which works as
 * a main class for plugins, just like JavaPlugin
 *
 * @see org.bukkit.plugin.java.JavaPlugin
 * @since 2.0.0
 */
@Suppress("unused")
abstract class APIPlugin : JavaPlugin() {

    val logger = APILogger(this)

    /**
     * Command Manager. Used to register a {@link Command}
     */
    lateinit var commandManager: CommandManager

    /**
     * Custom scheduler that takes advantage of Folia's scheduler
     * without the need to check all the time if the server is running Folia
     */
    lateinit var scheduler: Scheduler

    /**
     * bStats wrapper
     */
    lateinit var bStats: bStats
    protected open var bStatsServiceId = 0

    /**
     * @see ServerType
     */
    lateinit var serverType: ServerType

    /**
     * The library manager. Used to download libraries on runtime
     */
    lateinit var libraryManager: LibraryManager

    var debug: Boolean
        get() = logger.debug
        set(debug) {
            logger.debug = debug
        }

    final override fun onLoad() {
        this.serverType = ServerType.type
        this.libraryManager = LibraryManager.get(this)

        this.onPreLoad()

        logger.debug("&6Loading hooks...")
        loadHooks()
    }

    final override fun onEnable() {
        logger.debug("&6Enabling plugin...")
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
        scheduler = Scheduler.get(this, serverType == ServerType.FOLIA)

        if (bStatsServiceId != 0) {
            logger.debug("&6Loading bStats hook...")
            bStats = bStats(this, bStatsServiceId)
        }
    }

    /**
     * Called before loading AdriAPI's hooks
     */
    abstract fun onPreLoad()

    /**
     * Called after loading AdriAPI's hooks
     */
    abstract fun onPostLoad()

    /**
     * Called before unloading AdriAPI's hooks
     */
    abstract fun onUnload()

    /**
     * Registers a [Set] of [Command]
     *
     * @param commands The Set of commands
     * @see Command
     */
    protected fun registerCommands(commands: MutableSet<Command>) {
        commands.forEach {
            registerCommand(it)
        }
    }

    /**
     * Registers a [Command]
     *
     * @param command The command
     * @see Command
     */
    protected fun registerCommand(command: Command) {
        commandManager.registerCommand(command)
    }

    /**
     * Unregisters a [Command] by its name
     *
     * @param command The command
     * @see Command
     */
    protected fun unRegisterCommand(name: String) {
        this.commandManager.getCommand(name)
            .ifPresent(Consumer { command: Command? -> this.unRegisterCommand(command!!) })
    }

    /**
     * Unregisters a [Command]
     *
     * @param command The command
     * @see Command
     */
    protected fun unRegisterCommand(command: Command) {
        this.commandManager.unRegisterCommand(command)
    }

    /**
     * Registers a [Set] of [Listener]
     *
     * @param listeners The listeners
     * @see Listener
     */
    protected fun registerListeners(listeners: MutableSet<Listener>) {
        listeners.forEach {
            this.registerListener(it)
        }
    }

    /**
     * Registers a [Listener]
     *
     * @param listener The listener
     * @see Listener
     */
    protected fun registerListener(listener: Listener) {
        server.pluginManager.registerEvents(listener, this)
    }

    /**
     * @return The plugin's Logger
     * @see APIPlugin.getLogger
     */
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    @Deprecated("In favor of APIPlugin#getLogger()")
    fun getApiLogger(): APILogger {
        return logger
    }
}