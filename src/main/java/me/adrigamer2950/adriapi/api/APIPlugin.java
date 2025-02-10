package me.adrigamer2950.adriapi.api;

import lombok.Getter;
import lombok.NonNull;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.folia.Scheduler;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.adriapi.api.util.ServerType;
import me.adrigamer2950.adriapi.api.util.bStats;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
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

    private CommandManager<APIPlugin> commandManager;
    private Scheduler scheduler;
    private bStats bstats;
    private ServerType serverType;
    private BukkitAudiences adventure;

    @Override
    public final void onEnable() {
        this.serverType = ServerType.getType();

        this.adventure = BukkitAudiences.create(this);

        onPreLoad();
        loadHooks();
        onPostLoad();
    }

    private void loadHooks() {
        this.commandManager = new CommandManager<>(this);
        this.scheduler = new Scheduler(this);

        if (this.getBStatsServiceId() != 0) this.bstats = new bStats(this, this.getBStatsServiceId());
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
        this.onUnload();

        this.commandManager = null;
        this.scheduler = null;
        if (this.bstats != null) this.bstats.shutdown();
        this.bstats = null;
        this.serverType = null;
        this.adventure = null;
    }

    /**
     * Registers a {@link Set} of {@link Command}
     *
     * @param commands The Set of commands
     * @see Command
     */
    protected void registerCommands(@NonNull Set<@NonNull Command<? extends APIPlugin>> commands) {
        for (Command<? extends APIPlugin> command : commands)
            this.registerCommand(command);
    }

    /**
     * Registers a {@link Command}
     *
     * @param command The command
     * @see Command
     */
    protected void registerCommand(@NonNull Command<? extends APIPlugin> command) {
        this.commandManager.registerCommand(command);
    }

    /**
     * Registers a {@link Set} of {@link Listener}
     *
     * @param listeners The listeners
     * @see Listener
     */
    protected void registerListeners(@NonNull Set<@NonNull Listener> listeners) {
        for (Listener listener : listeners)
            this.registerListener(listener);
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
}
