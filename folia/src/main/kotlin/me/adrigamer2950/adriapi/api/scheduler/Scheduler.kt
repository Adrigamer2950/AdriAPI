package me.adrigamer2950.adriapi.api.scheduler

import me.adrigamer2950.adriapi.api.scheduler.impl.FoliaScheduler
import me.adrigamer2950.adriapi.api.scheduler.impl.PaperScheduler
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval

interface Scheduler {

    /**
     * Runs a task on the next server tick
     *
     * @param runnable The task to run
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    @Deprecated("Use run(Consumer<ScheduledTask>, Boolean) instead",
        ReplaceWith("run(async) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun run(runnable: Runnable, async: Boolean = false): ScheduledTask {
        return run(async) { runnable.run() }
    }

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
     * @param runnable The task to run
     * @return The task
     */
    @Deprecated("Use runAsync(Consumer<ScheduledTask>) instead",
        ReplaceWith("run { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runAsync(runnable: Runnable): ScheduledTask {
        return runAsync { runnable.run() }
    }

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
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    @Deprecated("Use runLater(Consumer<ScheduledTask>, Long, Boolean) instead",
        ReplaceWith("run(delay, async) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runLater(runnable: Runnable, delay: Long, async: Boolean = false): ScheduledTask {
        return runLater(delay, async) { runnable.run() }
    }

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
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @return The task
     */
    @Deprecated("Use runAsyncLater(Consumer<ScheduledTask>, Long) instead",
        ReplaceWith("run(delay) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runAsyncLater(runnable: Runnable, delay: Long): ScheduledTask {
        return runAsyncLater(delay) { runnable.run() }
    }

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
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    @Deprecated("Use runTimer(Consumer<ScheduledTask>, Long, Long, Boolean) instead",
        ReplaceWith("run(delay, period, async) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runTimer(runnable: Runnable, delay: Long, period: Long, async: Boolean = false): ScheduledTask {
        return runTimer(delay, period, async) { runnable.run() }
    }

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
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @return The task
     */
    @Deprecated("Use runAsyncTimer(Consumer<ScheduledTask>, Long, Long) instead",
        ReplaceWith("run(delay, period) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runAsyncTimer(runnable: Runnable, delay: Long, period: Long): ScheduledTask {
        return runAsyncTimer(delay, period) { runnable.run() }
    }

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
     * @param runnable The task to run
     * @param entity   The entity to run the task on
     * @param async    Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    @Deprecated("Use runOnEntity(Consumer<ScheduledTask>, Entity, Boolean) instead",
        ReplaceWith("run(entity, async) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runOnEntity(runnable: Runnable, entity: Entity, async: Boolean = false): ScheduledTask {
        return runOnEntity(entity, async) { runnable.run() }
    }

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
     * @param runnable The task to run
     * @param entity   The entity to run the task on
     * @param delay    The delay in ticks
     * @param async    Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    @Deprecated("Use runLaterOnEntity(Consumer<ScheduledTask>, Entity, Long, Boolean) instead",
        ReplaceWith("run(entity, delay, async) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runLaterOnEntity(runnable: Runnable, entity: Entity, delay: Long, async: Boolean = false): ScheduledTask {
        return runLaterOnEntity(entity, delay, async) { runnable.run() }
    }

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
     * @param runnable The task to run
     * @param entity   The entity to run the task on
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @param async    Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return         The task
     */
    @Deprecated("Use runTimerOnEntity(Consumer<ScheduledTask>, Entity, Long, Long, Boolean) instead",
        ReplaceWith("run(entity, delay, period, async) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runTimerOnEntity(
        runnable: Runnable,
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean = false,
    ): ScheduledTask {
        return runTimerOnEntity(entity, delay, period, async) { runnable.run() }
    }

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
     * @param runnable  The task to run
     * @param world     The world the region is in
     * @param chunkX    The X coordinate of the region
     * @param chunkZ    The Z coordinate of the region
     * @param async     Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return          The task
     */
    @Deprecated("Use runAtRegion(Consumer<ScheduledTask>, World, Int, Int, Boolean) instead",
        ReplaceWith("run(world, chunkX, chunkZ, async) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runAtRegion(runnable: Runnable, world: World, chunkX: Int, chunkZ: Int, async: Boolean = false): ScheduledTask {
        return runAtRegion(world, chunkX, chunkZ, async) { runnable.run() }
    }

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
     * @param runnable  The task to run
     * @param world     The world the region is in
     * @param chunkX    The X coordinate of the region
     * @param chunkZ    The Z coordinate of the region
     * @param delay     The delay in ticks
     * @param async     Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return          The task
     */
    @Deprecated("Use runLaterAtRegion(Consumer<ScheduledTask>, World, Int, Int, Long, Boolean) instead",
        ReplaceWith("run(world, chunkX, chunkZ, delay, async) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runLaterAtRegion(
        runnable: Runnable,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        async: Boolean = false,
    ): ScheduledTask {
        return runLaterAtRegion(world, chunkX, chunkZ, delay, async) { runnable.run() }
    }

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
     * @param runnable  The task to run
     * @param world     The world the region is in
     * @param chunkX    The X coordinate of the region
     * @param chunkZ    The Z coordinate of the region
     * @param delay     The delay in ticks
     * @param period    The period in ticks
     * @param async     Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return          The task
     */
    @Deprecated("Use runTimerAtRegion(Consumer<ScheduledTask>, World, Int, Int, Long, Long, Boolean) instead",
        ReplaceWith("run(world, chunkX, chunkZ, delay, period, async) { task -> /* your code here */ }")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runTimerAtRegion(
        runnable: Runnable,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        period: Long,
        async: Boolean = false,
    ): ScheduledTask {
        return runTimerAtRegion(world, chunkX, chunkZ, delay, period, async) { runnable.run() }
    }

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
