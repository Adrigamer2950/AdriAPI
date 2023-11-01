package me.adrigamer2950.adriapi.api.folia;

import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class Scheduler {

    private final JavaPlugin plugin;
    private static boolean isFoliaServer;

    static {
        try {
            Class.forName("io.papermc.paper.threadedregions.scheduler.RegionScheduler");
            isFoliaServer = true;
        } catch (ClassNotFoundException e) {
            isFoliaServer = false;
        }
    }

    public Scheduler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static boolean isFoliaServer() {
        return isFoliaServer;
    }

    public void run(Runnable runnable) {
        if(isFoliaServer())
            Bukkit.getGlobalRegionScheduler().run(plugin, t -> runnable.run());
        else
            Bukkit.getScheduler().runTask(plugin, runnable);
    }

    public void runAsync(Runnable runnable) {
        if(isFoliaServer())
            Bukkit.getGlobalRegionScheduler().execute(plugin, runnable);
        else
            Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }

    public Task runLater(Runnable runnable, long delay) {
        if(isFoliaServer())
            return new Task(Bukkit.getGlobalRegionScheduler().runDelayed(plugin, t -> runnable.run(), delay));
        else
            return new Task(Bukkit.getScheduler().runTaskLater(plugin, runnable, delay));
    }

    public Task runLaterAsync(Runnable runnable, long delay) {
        if(isFoliaServer())
            return new Task(Bukkit.getGlobalRegionScheduler().runDelayed(plugin, t -> runnable.run(), delay));
        else
            return new Task(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay));
    }

    public Task runTimer(Runnable runnable, long delay, long period) {
        if(isFoliaServer())
            return new Task(Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, t -> runnable.run(), delay < 1 ? 1 : delay, period));
        else
            return new Task(Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period));
    }

    public Task runTimerAsync(Runnable runnable, long delay, long period) {
        if(isFoliaServer())
            return new Task(Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, t -> runnable.run(), delay < 1 ? 1 : delay, period));
        else
            return new Task(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period));
    }

    public static class Task {

        private final Object task;

        public Task(Object task) {
            this.task = task;
        }

        public void cancel() {
            if(task instanceof BukkitTask)
                ((BukkitTask) task).cancel();
            else
                ((ScheduledTask) task).cancel();
        }
    }
}
