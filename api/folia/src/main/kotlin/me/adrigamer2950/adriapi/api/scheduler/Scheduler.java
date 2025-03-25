package me.adrigamer2950.adriapi.api.scheduler;

import me.adrigamer2950.adriapi.api.scheduler.folia.FoliaScheduler;
import me.adrigamer2950.adriapi.api.scheduler.paper.PaperScheduler;
import me.adrigamer2950.adriapi.api.scheduler.task.ScheduledTask;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public interface Scheduler {

    /**
     * Creates a new {@link Scheduler} instance for the given plugin
     *
     * @param plugin The plugin to get the scheduler for
     * @return The scheduler instance
     */
    static Scheduler get(Plugin plugin) {
        return get(plugin, false);
    }

    /**
     * Creates a new {@link Scheduler} instance for the given plugin
     *
     * @param plugin The plugin to get the scheduler for
     * @param folia Whether to use the Folia scheduler
     * @return The scheduler instance
     */
    static Scheduler get(Plugin plugin, boolean folia) {
        return folia ? new FoliaScheduler(plugin) : new PaperScheduler(plugin);
    }

    /**
     * Runs a task on the next server tick
     *
     * @param runnable The task to run
     * @return The task
     */
    ScheduledTask run(Runnable runnable);

    /**
     * Runs a task after a delay
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @return The task
     */
    ScheduledTask runLater(Runnable runnable, long delay);

    /**
     * Runs a task repeatedly after a delay
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @return The task
     */
    ScheduledTask runTimer(Runnable runnable, long delay, long period);

    /**
     * Runs a task asynchronously
     *
     * @param runnable The task to run
     * @return The task
     */
    ScheduledTask runAsync(Runnable runnable);

    /**
     * Runs a task asynchronously after a delay
     * @param runnable The task to run
     * @param delay The delay in ticks
     * @return The task
     */
    ScheduledTask runAsyncLater(Runnable runnable, long delay);

    /**
     * Runs a task asynchronously repeatedly after a delay
     * @param runnable The task to run
     * @param delay The delay in ticks
     * @param period The period in ticks
     * @return The task
     */
    ScheduledTask runAsyncTimer(Runnable runnable, long delay, long period);

    /**
     * Runs a task on a specific entity
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @return The task
     */
    ScheduledTask runOnEntity(Runnable runnable, Entity entity);

    /**
     * Runs a task on a specific entity
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    ScheduledTask runOnEntity(Runnable runnable, Entity entity, boolean async);

    /**
     * Runs a task on a specific entity after a delay
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @return The task
     */
    ScheduledTask runLaterOnEntity(Runnable runnable, Entity entity, long delay);

    /**
     * Runs a task on a specific entity after a delay
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    ScheduledTask runLaterOnEntity(Runnable runnable, Entity entity, long delay, boolean async);

    /**
     * Runs a task on a specific entity repeatedly after a delay
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @param period The period in ticks
     * @return The task
     */
    ScheduledTask runTimerOnEntity(Runnable runnable, Entity entity, long delay, long period);

    /**
     * Runs a task on a specific entity repeatedly after a delay
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @param period The period in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    ScheduledTask runTimerOnEntity(Runnable runnable, Entity entity, long delay, long period, boolean async);

    /**
     * Runs a task on a specific region
     * @param runnable The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @return The task
     */
    ScheduledTask runAtRegion(Runnable runnable, World world, int chunkX, int chunkZ);

    /**
     * Runs a task on a specific region
     * @param runnable The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    ScheduledTask runAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, boolean async);

    /**
     * Runs a task on a specific region after a delay
     * @param runnable The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param delay The delay in ticks
     * @return The task
     */
    ScheduledTask runLaterAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay);

    /**
     * Runs a task on a specific region after a delay
     * @param runnable The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param delay The delay in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    ScheduledTask runLaterAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay, boolean async);

    /**
     * Runs a task on a specific region repeatedly after a delay
     * @param runnable The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param delay The delay in ticks
     * @param period The period in ticks
     * @return The task
     */
    ScheduledTask runTimerAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay, long period);

    /**
     * Runs a task on a specific region repeatedly after a delay
     * @param runnable The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param delay The delay in ticks
     * @param period The period in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    ScheduledTask runTimerAtRegion(Runnable runnable, World world, int chunkX, int chunkZ, long delay, long period, boolean async);
}
