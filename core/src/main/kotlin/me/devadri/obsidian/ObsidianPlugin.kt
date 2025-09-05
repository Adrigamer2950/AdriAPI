package me.devadri.obsidian

import com.alessiodp.libby.Library
import me.devadri.obsidian.util.bStats
import me.devadri.obsidian.command.Command
import me.devadri.obsidian.command.manager.CommandManager
import me.devadri.obsidian.internal.BuildConstants
import me.devadri.obsidian.internal.InventoriesListener
import me.devadri.obsidian.library.manager.LibraryManager
import me.devadri.obsidian.library.manager.LibraryManagerImpl
import me.devadri.obsidian.logger.Logger
import me.devadri.obsidian.logger.impl.LoggerImpl
import me.devadri.obsidian.scheduler.provider.SchedulerProvider
import me.devadri.obsidian.scheduler.task.ScheduledTask
import me.devadri.obsidian.util.ServerType
import org.bukkit.event.Listener
import org.bukkit.plugin.PluginDescriptionFile
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.java.JavaPluginLoader
import org.reflections.Reflections
import org.reflections.util.ConfigurationBuilder
import java.io.File

/**
 * Extension of JavaPlugin which works as
 * a main class for plugins, just like JavaPlugin
 *
 * @see JavaPlugin
 * @since 2.0.0
 */
@Suppress("unused")
abstract class ObsidianPlugin : JavaPlugin {

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

    @JvmField
    val logger: Logger = LoggerImpl(this)

    /**
     * Standard Bukkit logger. Might be useful if API's logger has some issue logging something
     */
    @JvmField
    val bukkitLogger: java.util.logging.Logger = super.logger

    /**
     * Command Manager. Used to register a Command
     */
    lateinit var commandManager: CommandManager
        protected set

    /**
     * Custom scheduler that takes advantage of Folia's scheduler
     * without the need to check all the time if the server is running Folia
     */
    lateinit var scheduler: SchedulerProvider
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

        val libMan = LibraryManagerImpl(this, "Obsidian")

        libMan.addRepository("https://jitpack.io")
        libMan.loadLibraries(
            Library.builder()
                .groupId("org.fusesource.jansi")
                .artifactId("jansi")
                .version(BuildConstants.JANSI_VERSION)
                .resolveTransitiveDependencies(true)
                .build(),
            Library.builder()
                .groupId("com.github.Adrigamer2950")
                .artifactId("reflections")
                .version(BuildConstants.REFLECTIONS_VERSION)
                .resolveTransitiveDependencies(true)
                .build(),
            Library.builder()
                .groupId("com.github.cryptomorin")
                .artifactId("XSeries")
                .version(BuildConstants.XSERIES_VERSION)
                .resolveTransitiveDependencies(true)
                .build()
        )

        System.getProperty("adriapi.debug")?.let {
            if (it != "true") return@let

            this.debug = true
            logger.debug("&6Debug mode was enabled because -Dadriapi.debug flag was set to true")
        }

        this.onPreLoad()
    }

    final override fun onEnable() {
        logger.debug("&6Loading hooks...")
        loadHooks()

        registerListener(InventoriesListener(this))

        logger.debug("&6Auto-registering listeners & commands...")
        autoRegister()

        onPostLoad()
    }

    final override fun onDisable() {
        logger.debug("&6Disabling plugin...")
        onUnload()

        if (this::bStats.isInitialized)
            bStats.shutdown()

        if (this::commandManager.isInitialized) {
            this.commandManager.commands.toList().forEach { this.commandManager.unRegisterCommand(it) }
        }

        logger.debug("&6Cancelling all scheduler tasks...")
        ScheduledTask.cancelAll(this)
    }

    private fun loadHooks() {
        logger.debug("&6Loading Command Manager...")
        commandManager = CommandManager(this)

        logger.debug("&6Loading Scheduler...")
        scheduler = SchedulerProvider.create(this, ServerType.type == ServerType.FOLIA)

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
     * Registers a [me.devadri.obsidian.command.Command]
     *
     * @param commands The commands
     * @see me.devadri.obsidian.command.Command
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

    private fun autoRegister() {
        val reflections = Reflections(
            ConfigurationBuilder()
                .forPackages(this::class.java.packageName)
                .disableLogging()
        )
        val handler = AutoRegisterHandler(this)

        reflections.getTypesAnnotatedWith(AutoRegister::class.java).forEach {
            if (it.packageName.startsWith("${this::class.java.packageName}.libs")) return@forEach // Ignore libs package

            try {
                logger.debug("&6Found a class annotated with AutoRegister. Registering `${it.simpleName}`...")

                handler.registerType(it)
            } catch (ex: Exception) {
                logger.error("&cFailed to auto-register `${it.simpleName}`. Skipping... ${ex.message}", ex)
            }
        }
    }
}