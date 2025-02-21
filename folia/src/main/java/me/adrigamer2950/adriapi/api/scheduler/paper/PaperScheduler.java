package me.adrigamer2950.adriapi.api.scheduler.paper;

import lombok.RequiredArgsConstructor;
import me.adrigamer2950.adriapi.api.scheduler.Scheduler;
import me.adrigamer2950.adriapi.api.scheduler.task.ScheduledTask;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

@RequiredArgsConstructor
public class PaperScheduler implements Scheduler {

    private final Plugin plugin;

    @Override
    public ScheduledTask run(Runnable runnable) {
        return new ScheduledTask(Bukkit.getScheduler().runTask(plugin, runnable), plugin);
    }

    @Override
    public ScheduledTask runLater(Runnable runnable, long delay) {
        return new ScheduledTask(Bukkit.getScheduler().runTaskLater(plugin, runnable, delay), plugin);
    }

    @Override
    public ScheduledTask runTimer(Runnable runnable, long delay, long period) {
        return new ScheduledTask(Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period), plugin);
    }

    @Override
    public ScheduledTask runAsync(Runnable runnable) {
        return new ScheduledTask(Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable), plugin);
    }

    @Override
    public ScheduledTask runAsyncLater(Runnable runnable, long delay) {
        return new ScheduledTask(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay), plugin);
    }

    @Override
    public ScheduledTask runAsyncTimer(Runnable runnable, long delay, long period) {
        return new ScheduledTask(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period), plugin);
    }

    @Override
    public ScheduledTask runOnEntity(Runnable runnable, Entity entity) {
        return runOnEntity(runnable, entity, false);
    }

    @Override
    public ScheduledTask runOnEntity(Runnable runnable, Entity entity, boolean async) {
        return async ? runAsync(runnable) : run(runnable);
    }

    @Override
    public ScheduledTask runLaterOnEntity(Runnable runnable, Entity entity, long delay) {
        return runLaterOnEntity(runnable, entity, delay, false);
    }

    @Override
    public ScheduledTask runLaterOnEntity(Runnable runnable, Entity entity, long delay, boolean async) {
        return async ? runAsync(runnable) : run(runnable);
    }

    @Override
    public ScheduledTask runTimerOnEntity(Runnable runnable, Entity entity, long delay, long period) {
        return runTimerOnEntity(runnable, entity, delay, period, false);
    }

    @Override
    public ScheduledTask runTimerOnEntity(Runnable runnable, Entity entity, long delay, long period, boolean async) {
        return async ? runAsync(runnable) : run(runnable);
    }

    @Override
    public ScheduledTask runAtRegion(Runnable runnable, World world, int chunkX, int chunkZ) {
        return runAtRegion(runnable, world, chunkX, chunkZ, false);
    }

    @Override
    public ScheduledTask runAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, boolean async) {
        return async ? runAsync(runnable) : run(runnable);
    }

    @Override
    public ScheduledTask runLaterAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay) {
        return runLaterAtRegion(runnable, world, chunkX, chunkZ, delay, false);
    }

    @Override
    public ScheduledTask runLaterAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay, boolean async) {
        return async ? runAsyncLater(runnable, delay) : runLater(runnable, delay);
    }

    @Override
    public ScheduledTask runTimerAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay, long period) {
        return runTimerAtRegion(runnable, world, chunkX, chunkZ, delay, period, false);
    }

    @Override
    public ScheduledTask runTimerAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay, long period, boolean async) {
        return async ? runAsyncTimer(runnable, delay, period) : runTimer(runnable, delay, period);
    }
}
