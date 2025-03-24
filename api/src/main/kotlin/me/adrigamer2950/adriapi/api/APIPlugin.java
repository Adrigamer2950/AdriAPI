package me.adrigamer2950.adriapi.api;

import lombok.Getter;
import lombok.NonNull;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.scheduler.Scheduler;
import me.adrigamer2950.adriapi.api.library.manager.LibraryManager;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.adriapi.api.util.ServerType;
import me.adrigamer2950.adriapi.api.util.bStats;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

import java.util.Set;

/**
 * APIPlugin is an extension of the {@link JavaPlugin} class
 * that gives you full control over AdriAPI's functions.
 * You should make your plugin's main class extend this class,
 * because it's necessary for many functions AdriAPI has.
 *
 * @see org.bukkit.plugin.java.JavaPlugin
 * @since 2.0.0
 */
@SuppressWarnings({"unused"})
@Getter
public abstract class APIPlugin extends JavaPlugin {

    private final APILogger logger = new APILogger(this);

    /**
     * Command Manager. Used to register a {@link Command}
     */
    private CommandManager commandManager;

    /**
     * Custom scheduler that takes advantage of Folia's scheduler
     * without the need to check all the time if the server is running Folia
     */
    private Scheduler scheduler;

    /**
     * bStats wrapper
     */
    private bStats bstats;

    /**
     * @see ServerType
     */
    private ServerType serverType;

    /**
     * The library manager. Used to download libraries on runtime
     */
    private LibraryManager libraryManager;

    @Override
    public final void onLoad() {
        this.serverType = ServerType.getType();
        this.libraryManager = LibraryManager.get(this);

        onPreLoad();

        this.getLogger().debug("&6Loading hooks...");
        loadHooks();
    }

    @Override
    public final void onEnable() {
        this.getLogger().debug("&6Enabling plugin...");
        onPostLoad();
    }

    private void loadHooks() {
        this.getLogger().debug("&6Loading Command Manager...");
        this.commandManager = new CommandManager(this);

        this.getLogger().debug("&6Loading Scheduler...");
        this.scheduler = Scheduler.get(this, this.getServerType().equals(ServerType.FOLIA));

        if (this.getBStatsServiceId() != 0) {
            this.getLogger().debug("&6Loading bStats hook...");
            this.bstats = new bStats(this, this.getBStatsServiceId());
        }
    }

    /**
     * Called before loading AdriAPI's hooks
     */
    public abstract void onPreLoad();

    /**
     * Called after loading AdriAPI's hooks
     */
    public abstract void onPostLoad();

    /**
     * Called before unloading AdriAPI's hooks
     */
    public abstract void onUnload();

    @Override
    public final void onDisable() {
        this.getLogger().debug("&6Unloading plugin...");
        this.onUnload();

        this.commandManager = null;
        this.scheduler = null;
        if (this.bstats != null) this.bstats.shutdown();
        this.bstats = null;
        this.serverType = null;
        this.libraryManager = null;
    }

    /**
     * Registers a {@link Set} of {@link Command}
     *
     * @param commands The Set of commands
     * @see Command
     */
    protected void registerCommands(@NonNull Set<@NonNull Command> commands) {
        commands.forEach(this::registerCommand);
    }

    /**
     * Registers a {@link Command}
     *
     * @param command The command
     * @see Command
     */
    protected void registerCommand(@NonNull Command command) {
        this.commandManager.registerCommand(command);
    }

    protected void unRegisterCommand(@NonNull String name) {
        this.commandManager.getCommand(name).ifPresent(this::unRegisterCommand);
    }

    protected void unRegisterCommand(@NonNull Command command) {
        this.commandManager.unRegisterCommand(command);
    }

    /**
     * Registers a {@link Set} of {@link Listener}
     *
     * @param listeners The listeners
     * @see Listener
     */
    protected void registerListeners(@NonNull Set<@NonNull Listener> listeners) {
        listeners.forEach(this::registerListener);
    }

    /**
     * Registers a {@link Listener}
     *
     * @param listener The listener
     * @see Listener
     */
    protected void registerListener(@NonNull Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    /**
     * Override with your bStats plugin id to enable bStats module
     *
     * @return Your bStats plugin id, 0 if you didn't override this method
     */
    protected int getBStatsServiceId() {
        return 0;
    }

    /**
     * @return The plugin's Logger
     * @see APIPlugin#getLogger()
     * @deprecated In favor of {@link APIPlugin#getLogger()}
     */
    @ApiStatus.ScheduledForRemoval(inVersion = "3.0.0")
    @Deprecated(forRemoval = true)
    public APILogger getApiLogger() {
        return this.getLogger();
    }

    public boolean isDebug() {
        return this.logger.isDebug();
    }

    protected void setDebug(boolean debug) {
        this.logger.setDebug(debug);
    }
}
