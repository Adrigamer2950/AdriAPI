package me.adrigamer2950.adriapi.api.scheduler.folia;

import lombok.RequiredArgsConstructor;
import me.adrigamer2950.adriapi.api.scheduler.Scheduler;
import me.adrigamer2950.adriapi.api.scheduler.task.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class FoliaScheduler implements Scheduler {

    private final Plugin plugin;

    @Override
    public ScheduledTask run(Runnable runnable) {
        return new ScheduledTask(Bukkit.getGlobalRegionScheduler().run(plugin, t -> runnable.run()), plugin);
    }

    @Override
    public ScheduledTask runLater(Runnable runnable, long delay) {
        return new ScheduledTask(Bukkit.getGlobalRegionScheduler().runDelayed(plugin, t -> runnable.run(), delay), plugin);
    }

    @Override
    public ScheduledTask runTimer(Runnable runnable, long delay, long period) {
        return new ScheduledTask(Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, t -> runnable.run(), delay, period), plugin);
    }

    @Override
    public ScheduledTask runAsync(Runnable runnable) {
        return new ScheduledTask(Bukkit.getAsyncScheduler().runNow(plugin, t -> runnable.run()), plugin);
    }

    @Override
    public ScheduledTask runAsyncLater(Runnable runnable, long delay) {
        return new ScheduledTask(Bukkit.getAsyncScheduler().runDelayed(plugin, t -> runnable.run(), delay / 20, TimeUnit.SECONDS), plugin);
    }

    @Override
    public ScheduledTask runAsyncTimer(Runnable runnable, long delay, long period) {
        return new ScheduledTask(Bukkit.getAsyncScheduler().runAtFixedRate(plugin, t -> runnable.run(), delay / 20, period / 20, TimeUnit.SECONDS), plugin);
    }

    @Override
    public ScheduledTask runOnEntity(Runnable runnable, Entity entity) {
        return new ScheduledTask(entity.getScheduler().run(plugin, t -> runnable.run(), null), plugin);
    }

    @Override
    public ScheduledTask runOnEntity(Runnable runnable, Entity entity, boolean async) {
        return this.runOnEntity(runnable, entity);
    }

    @Override
    public ScheduledTask runLaterOnEntity(Runnable runnable, Entity entity, long delay) {
        return new ScheduledTask(entity.getScheduler().runDelayed(plugin, t -> runnable.run(), null, delay), plugin);
    }

    @Override
    public ScheduledTask runLaterOnEntity(Runnable runnable, Entity entity, long delay, boolean async) {
        return this.runLaterOnEntity(runnable, entity, delay);
    }

    @Override
    public ScheduledTask runTimerOnEntity(Runnable runnable, Entity entity, long delay, long period) {
        return new ScheduledTask(entity.getScheduler().runAtFixedRate(plugin, t -> runnable.run(), null, delay, period), plugin);
    }

    @Override
    public ScheduledTask runTimerOnEntity(Runnable runnable, Entity entity, long delay, long period, boolean async) {
        return this.runTimerOnEntity(runnable, entity, delay, period);
    }

    @Override
    public ScheduledTask runAtRegion(Runnable runnable, World world, int chunkX, int chunkZ) {
        return new ScheduledTask(Bukkit.getRegionScheduler().run(plugin, world, chunkX, chunkZ, t -> runnable.run()), plugin);
    }

    @Override
    public ScheduledTask runAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, boolean async) {
        return this.runAtRegion(runnable, world, chunkX, chunkZ);
    }

    @Override
    public ScheduledTask runLaterAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay) {
        return new ScheduledTask(Bukkit.getRegionScheduler().runDelayed(plugin, world, chunkX, chunkZ, t -> runnable.run(), delay), plugin);
    }

    @Override
    public ScheduledTask runLaterAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay, boolean async) {
        return this.runLaterAtRegion(runnable, world, chunkX, chunkZ, delay);
    }

    @Override
    public ScheduledTask runTimerAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay, long period) {
        return new ScheduledTask(Bukkit.getRegionScheduler().runAtFixedRate(plugin, world, chunkX, chunkZ, t -> runnable.run(), delay, period), plugin);
    }

    @Override
    public ScheduledTask runTimerAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay, long period, boolean async) {
        return this.runTimerAtRegion(runnable, world, chunkX, chunkZ, delay, period);
    }
}
