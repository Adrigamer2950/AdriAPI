package me.adrigamer2950.adriapi.api;

import lombok.Getter;
import lombok.NonNull;
import me.adrigamer2950.adriapi.api.command.Command;
import me.adrigamer2950.adriapi.api.command.manager.CommandManager;
import me.adrigamer2950.adriapi.api.folia.Scheduler;
import me.adrigamer2950.adriapi.api.logger.APILogger;
import me.adrigamer2950.adriapi.api.util.ServerType;
import me.adrigamer2950.adriapi.api.util.bStats;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"deprecation", "unused"})
@Getter
public abstract class APIPlugin extends JavaPlugin {

    private APILogger apiLogger;

    private CommandManager<APIPlugin> commandManager;
    private Scheduler scheduler;
    private bStats bstats;

    private ServerType serverType;

    @Override
    public final void onEnable() {
        this.apiLogger = new APILogger(Objects.requireNonNull(this.getDescription().getPrefix() == null ? this.getDescription().getName() : this.getDescription().getPrefix()), this.getLogger());

        new bStats(this, 20135);

        this.serverType = ServerType.getType();

        onPreLoad();
        loadHooks();
        onPostLoad();
    }

    private void loadHooks() {
        this.commandManager = new CommandManager<>(this);
        this.scheduler = new Scheduler(this);

        if (this.getBStatsServiceId() != 0) this.bstats = new bStats(this, this.getBStatsServiceId());
    }

    public abstract void onPreLoad();

    public abstract void onPostLoad();

    public abstract void onUnload();

    @Override
    public final void onDisable() {
        this.onUnload();

        this.commandManager = null;
        this.scheduler = null;

        if (this.bstats != null) this.bstats.shutdown();
        this.bstats = null;
    }

    protected void registerCommands(@NonNull Set<@NonNull Command<? extends APIPlugin>> commands) {
        for (Command<? extends APIPlugin> command : commands) {
            this.registerCommand(command);
        }
    }

    protected void registerCommand(@NonNull Command<? extends APIPlugin> command) {
        this.commandManager.registerCommand(command);
    }

    protected void registerListeners(@NonNull Set<@NonNull Listener> listeners) {
        for (Listener listener : listeners) {
            this.registerListener(listener);
        }
    }

    protected void registerListener(@NonNull Listener listener) {
        getServer().getPluginManager().registerEvents(listener, this);
    }

    protected int getBStatsServiceId() {
        return 0;
    }
}
