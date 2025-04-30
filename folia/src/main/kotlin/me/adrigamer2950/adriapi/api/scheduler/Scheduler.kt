package me.adrigamer2950.adriapi.api.scheduler

import me.adrigamer2950.adriapi.api.scheduler.impl.FoliaScheduler
import me.adrigamer2950.adriapi.api.scheduler.impl.PaperScheduler
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin
import org.jetbrains.annotations.ApiStatus.ScheduledForRemoval
import java.util.function.Consumer

interface Scheduler {

    /**
     * Runs a task on the next server tick
     *
     * @param runnable The task to run
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    @Deprecated("Use run(Consumer<ScheduledTask>, Boolean) instead",
        ReplaceWith("run({ task -> /* your code here */ }, async)")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun run(runnable: Runnable, async: Boolean = false): ScheduledTask {
        return run(Consumer { runnable.run() }, async)
    }

    /**
     * Runs a task on the next server tick
     *
     * @param consumer The task to run
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    fun run(consumer: Consumer<ScheduledTask>, async: Boolean = false): ScheduledTask

    /**
     * Runs a task on the next server tick
     *
     * @param runnable The task to run
     * @return The task
     */
    @Deprecated("Use runAsync(Consumer<ScheduledTask>) instead",
        ReplaceWith("run({ task -> /* your code here */ })")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runAsync(runnable: Runnable): ScheduledTask {
        return runAsync(Consumer { runnable.run() })
    }

    /**
     * Runs a task on the next server tick
     *
     * @param consumer The task to run
     * @return The task
     */
    fun runAsync(consumer: Consumer<ScheduledTask>): ScheduledTask

    /**
     * Runs a task after a delay
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    @Deprecated("Use runLater(Consumer<ScheduledTask>, Long, Boolean) instead",
        ReplaceWith("run({ task -> /* your code here */ }, delay, async)")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runLater(runnable: Runnable, delay: Long, async: Boolean = false): ScheduledTask {
        return runLater(Consumer { runnable.run() }, delay, async)
    }

    /**
     * Runs a task after a delay
     *
     * @param consumer The task to run
     * @param delay    The delay in ticks
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    fun runLater(consumer: Consumer<ScheduledTask>, delay: Long, async: Boolean = false): ScheduledTask

    /**
     * Runs a task after a delay
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @return The task
     */
    @Deprecated("Use runAsyncLater(Consumer<ScheduledTask>, Long) instead",
        ReplaceWith("run({ task -> /* your code here */ }, delay)")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runAsyncLater(runnable: Runnable, delay: Long): ScheduledTask {
        return runAsyncLater(Consumer { runnable.run() }, delay)
    }

    /**
     * Runs a task after a delay
     *
     * @param consumer The task to run
     * @param delay    The delay in ticks
     * @return The task
     */
    fun runAsyncLater(consumer: Consumer<ScheduledTask>, delay: Long): ScheduledTask

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
        ReplaceWith("run({ task -> /* your code here */ }, delay, period, async)")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runTimer(runnable: Runnable, delay: Long, period: Long, async: Boolean = false): ScheduledTask {
        return runTimer(Consumer { runnable.run() }, delay, period, async)
    }

    /**
     * Runs a task repeatedly after a delay
     *
     * @param consumer The task to run
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @param async Whether to run the task asynchronously
     * @return The task
     */
    fun runTimer(consumer: Consumer<ScheduledTask>, delay: Long, period: Long, async: Boolean = false): ScheduledTask

    /**
     * Runs a task repeatedly after a delay
     *
     * @param runnable The task to run
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @return The task
     */
    @Deprecated("Use runAsyncTimer(Consumer<ScheduledTask>, Long, Long) instead",
        ReplaceWith("run({ task -> /* your code here */ }, delay, period)")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runAsyncTimer(runnable: Runnable, delay: Long, period: Long): ScheduledTask {
        return runAsyncTimer(Consumer { runnable.run() }, delay, period)
    }

    /**
     * Runs a task repeatedly after a delay
     *
     * @param consumer The task to run
     * @param delay    The delay in ticks
     * @param period   The period in ticks
     * @return The task
     */
    fun runAsyncTimer(consumer: Consumer<ScheduledTask>, delay: Long, period: Long): ScheduledTask

    /**
     * Runs a task on a specific entity
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    @Deprecated("Use runOnEntity(Consumer<ScheduledTask>, Entity, Boolean) instead",
        ReplaceWith("run({ task -> /* your code here */ }, entity, async)")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runOnEntity(runnable: Runnable, entity: Entity, async: Boolean = false): ScheduledTask {
        return runOnEntity(Consumer { runnable.run() }, entity, async)
    }

    /**
     * Runs a task on a specific entity
     * @param consumer The task to run
     * @param entity The entity to run the task on
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runOnEntity(consumer: Consumer<ScheduledTask>, entity: Entity, async: Boolean = false): ScheduledTask

    /**
     * Runs a task on a specific entity after a delay
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    @Deprecated("Use runLaterOnEntity(Consumer<ScheduledTask>, Entity, Long, Boolean) instead",
        ReplaceWith("run({ task -> /* your code here */ }, entity, delay, async)")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runLaterOnEntity(runnable: Runnable, entity: Entity, delay: Long, async: Boolean = false): ScheduledTask {
        return runLaterOnEntity(Consumer { runnable.run() }, entity, delay, async)
    }

    /**
     * Runs a task on a specific entity after a delay
     * @param consumer The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runLaterOnEntity(consumer: Consumer<ScheduledTask>, entity: Entity, delay: Long, async: Boolean = false): ScheduledTask

    /**
     * Runs a task on a specific entity repeatedly after a delay
     * @param runnable The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @param period The period in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    @Deprecated("Use runTimerOnEntity(Consumer<ScheduledTask>, Entity, Long, Long, Boolean) instead",
        ReplaceWith("run({ task -> /* your code here */ }, entity, delay, period, async)")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runTimerOnEntity(
        runnable: Runnable,
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean = false,
    ): ScheduledTask {
        return runTimerOnEntity(Consumer { runnable.run() }, entity, delay, period, async)
    }

    /**
     * Runs a task on a specific entity repeatedly after a delay
     * @param consumer The task to run
     * @param entity The entity to run the task on
     * @param delay The delay in ticks
     * @param period The period in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runTimerOnEntity(
        consumer: Consumer<ScheduledTask>,
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean = false,
    ): ScheduledTask

    /**
     * Runs a task on a specific region
     * @param runnable The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    @Deprecated("Use runAtRegion(Consumer<ScheduledTask>, World, Int, Int, Boolean) instead",
        ReplaceWith("run({ task -> /* your code here */ }, world, chunkX, chunkZ, async)")
    )
    @ScheduledForRemoval(inVersion = "2.6.0")
    fun runAtRegion(runnable: Runnable, world: World, chunkX: Int, chunkZ: Int, async: Boolean = false): ScheduledTask {
        return runAtRegion(Consumer { runnable.run() }, world, chunkX, chunkZ, async)
    }

    /**
     * Runs a task on a specific region
     * @param consumer The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runAtRegion(consumer: Consumer<ScheduledTask>, world: World, chunkX: Int, chunkZ: Int, async: Boolean = false): ScheduledTask

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
    @Deprecated("Use runLaterAtRegion(Consumer<ScheduledTask>, World, Int, Int, Long, Boolean) instead",
        ReplaceWith("run({ task -> /* your code here */ }, world, chunkX, chunkZ, delay, async)")
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
        return runLaterAtRegion(Consumer { runnable.run() }, world, chunkX, chunkZ, delay, async)
    }

    /**
     * Runs a task on a specific region after a delay
     * @param consumer The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param delay The delay in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runLaterAtRegion(
        consumer: Consumer<ScheduledTask>,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        async: Boolean = false,
    ): ScheduledTask

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
    @Deprecated("Use runTimerAtRegion(Consumer<ScheduledTask>, World, Int, Int, Long, Long, Boolean) instead",
        ReplaceWith("run({ task -> /* your code here */ }, world, chunkX, chunkZ, delay, period, async)")
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
        return runTimerAtRegion(Consumer { runnable.run() }, world, chunkX, chunkZ, delay, period, async)
    }

    /**
     * Runs a task on a specific region repeatedly after a delay
     * @param consumer The task to run
     * @param world The world the region is in
     * @param chunkX The X coordinate of the region
     * @param chunkZ The Z coordinate of the region
     * @param delay The delay in ticks
     * @param period The period in ticks
     * @param async Whether to run the task asynchronously (Doesn't do anything in Folia)
     * @return The task
     */
    fun runTimerAtRegion(
        consumer: Consumer<ScheduledTask>,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        period: Long,
        async: Boolean = false,
    ): ScheduledTask

    companion object {

        /**
         * Creates a new Scheduler instance for the given plugin
         *
         * @param plugin The plugin to get the scheduler for
         * @param folia Whether to use the Folia scheduler
         * @return The scheduler instance
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
