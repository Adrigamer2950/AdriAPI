package me.devadri.obsidian.scheduler

import me.devadri.obsidian.scheduler.impl.FoliaScheduler
import me.devadri.obsidian.scheduler.impl.PaperScheduler
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin

@Deprecated("In favor of new refactor")
interface Scheduler {

    /**
     * Runs a task on the next server tick
     *
     * @param func The task to run
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    fun run(async: Boolean = false, func: (ScheduledTask) -> Unit): ScheduledTask

    /**
     * Runs a task on the next server tick
     *
     * @param func The task to run
     * @return The task
     */
    fun runAsync(func: (ScheduledTask) -> Unit): ScheduledTask

    /**
     * Runs a task after a delay
     *
     * @param func The task to run
     * @param delay    The delay in ticks
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    fun runLater(delay: Long, async: Boolean = false, func: (ScheduledTask) -> Unit): ScheduledTask

    /**
     * Runs a task after a delay
     *
     * @param delay    The delay in ticks
     * @param func     The task to run
     * @return The task
     */
    fun runAsyncLater(delay: Long, func: (ScheduledTask) -> Unit): ScheduledTask

    /**
     * Runs a task repeatedly after a delay
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @param async    Whether to run the task asynchronously
     * @param func     The task to run
     * @return The task
     */
    fun runTimer(delay: Long, period: Long, async: Boolean = false, func: (ScheduledTask) -> Unit): ScheduledTask

    /**
     * Runs a task repeatedly after a delay
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @param func     The task to run
     * @return The task
     */
    fun runAsyncTimer(delay: Long, period: Long, func: (ScheduledTask) -> Unit): ScheduledTask

    /**
     * Runs a task on a specific entity
     * @param entity The entity to run the task on
     * @param async  Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @param func   The task to run
     * @return The task
     */
    fun runOnEntity(entity: Entity, async: Boolean = false, func: (ScheduledTask) -> Unit): ScheduledTask

    /**
     * Runs a task on a specific entity after a delay
     * @param entity The entity to run the task on
     * @param delay  The delay in ticks
     * @param async  Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @param func   The task to run
     * @return The task
     */
    fun runLaterOnEntity(entity: Entity, delay: Long, async: Boolean = false, func: (ScheduledTask) -> Unit): ScheduledTask

    /**
     * Runs a task on a specific entity repeatedly after a delay
     * @param entity  The entity to run the task on
     * @param delay   The delay in ticks
     * @param period  The period in ticks
     * @param async   Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @param func    The task to run
     * @return        The task
     */
    fun runTimerOnEntity(
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean = false,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask

    /**
     * Runs a task on a specific region
     * @param world   The world the region is in
     * @param chunkX  The X coordinate of the region
     * @param chunkZ  The Z coordinate of the region
     * @param async   Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @param func    The task to run
     * @return        The task
     */
    fun runAtRegion(world: World, chunkX: Int, chunkZ: Int, async: Boolean = false, func: (ScheduledTask) -> Unit): ScheduledTask

    /**
     * Runs a task on a specific region after a delay
     * @param world   The world the region is in
     * @param chunkX  The X coordinate of the region
     * @param chunkZ  The Z coordinate of the region
     * @param delay   The delay in ticks
     * @param async   Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @param func    The task to run
     * @return        The task
     */
    fun runLaterAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        async: Boolean = false,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask

    /**
     * Runs a task on a specific region repeatedly after a delay
     * @param world   The world the region is in
     * @param chunkX  The X coordinate of the region
     * @param chunkZ  The Z coordinate of the region
     * @param delay   The delay in ticks
     * @param period  The period in ticks
     * @param async   Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @param func    The task to run
     * @return        The task
     */
    fun runTimerAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        period: Long,
        async: Boolean = false,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask

    companion object {

        /**
         * Creates a new Scheduler instance for the given plugin
         *
         * @param plugin  The plugin to get the scheduler for
         * @param folia   Whether to use the Folia scheduler
         * @return        The scheduler instance
         */
        @JvmOverloads
        @JvmStatic
        fun make(plugin: Plugin, folia: Boolean = false): Scheduler {
            return if (folia) {
                FoliaScheduler(plugin)
            } else {
                PaperScheduler(plugin)
            }
        }
    }
}
