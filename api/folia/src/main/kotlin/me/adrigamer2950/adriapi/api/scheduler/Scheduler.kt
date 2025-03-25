package me.adrigamer2950.adriapi.api.scheduler

import org.bukkit.World
import org.bukkit.entity.Entity

interface Scheduler {

    /**
     * Runs a task on the next server tick
     *
     * @param runnable The task to run
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    fun run(runnable: Runnable, async: Boolean = false): ScheduledTask

    /**
     * Runs a task on the next server tick
     *
     * @param runnable The task to run
     * @return The task
     */
    fun runAsync(runnable: Runnable): ScheduledTask

    /**
     * Runs a task after a delay
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    fun runLater(runnable: Runnable, delay: Long, async: Boolean = false): ScheduledTask

    /**
     * Runs a task after a delay
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @return The task
     */
    fun runAsyncLater(runnable: Runnable, delay: Long): ScheduledTask

    /**
     * Runs a task repeatedly after a delay
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    fun runTimer(runnable: Runnable, delay: Long, period: Long, async: Boolean = false): ScheduledTask

    /**
     * Runs a task repeatedly after a delay
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @return The task
     */
    fun runAsyncTimer(runnable: Runnable, delay: Long, period: Long): ScheduledTask

    /**
     * Runs a task on a specific entity
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runOnEntity(runnable: Runnable, entity: Entity, async: Boolean = false): ScheduledTask

    /**
     * Runs a task on a specific entity after a delay
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runLaterOnEntity(runnable: Runnable, entity: Entity, delay: Long, async: Boolean = false): ScheduledTask

    /**
     * Runs a task on a specific entity repeatedly after a delay
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @param period The period in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runTimerOnEntity(runnable: Runnable, entity: Entity, delay: Long, period: Long, async: Boolean = false): ScheduledTask

    /**
     * Runs a task on a specific region
     * @param runnable The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runAtRegion(runnable: Runnable, world: World, chunkX: Int, chunkZ: Int, async: Boolean = false): ScheduledTask

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
    fun runLaterAtRegion(runnable: Runnable, world: World, chunkX: Int, chunkZ: Int, delay: Long, async: Boolean = false): ScheduledTask

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
    fun runTimerAtRegion(runnable: Runnable, world: World, chunkX: Int, chunkZ: Int, delay: Long, period: Long, async: Boolean = false): ScheduledTask
}
