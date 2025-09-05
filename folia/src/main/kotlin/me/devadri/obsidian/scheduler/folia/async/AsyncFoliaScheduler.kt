package me.devadri.obsidian.scheduler.folia.async

import me.devadri.obsidian.scheduler.AsyncScheduler
import me.devadri.obsidian.scheduler.task.ScheduledTask
import me.devadri.obsidian.scheduler.util.TaskEither
import me.devadri.obsidian.scheduler.util.TickUtil
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin
import java.util.concurrent.TimeUnit

class AsyncFoliaScheduler(val plugin: Plugin) : AsyncScheduler {

    override fun run(func: (ScheduledTask) -> Unit): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            Bukkit.getAsyncScheduler().runNow(plugin) {
                func(task)
            }
        )

        return task
    }

    override fun runLater(
        unit: TimeUnit,
        delay: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            Bukkit.getAsyncScheduler().runDelayed(plugin, {
                func(task)
            }, delay, unit)
        )

        return task
    }

    override fun runTimer(
        unit: TimeUnit,
        delay: Long,
        period: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
                func(task)
            }, delay, period, unit)
        )

        return task
    }

    override fun runOnEntity(
        entity: Entity,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            entity.scheduler.run(plugin, {
                func(task)
            }, null)
        )

        return task
    }

    override fun runLaterOnEntity(
        entity: Entity,
        unit: TimeUnit,
        delay: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            entity.scheduler.runDelayed(plugin, {
                func(task)
            }, null, TickUtil.timeToTicks(unit, delay))
        )

        return task
    }

    override fun runTimerOnEntity(
        entity: Entity,
        unit: TimeUnit,
        delay: Long,
        period: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            entity.scheduler.runAtFixedRate(plugin, {
                func(task)
            }, null, TickUtil.timeToTicks(unit, delay), TickUtil.timeToTicks(unit, period))
        )

        return task
    }

    override fun runAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            Bukkit.getRegionScheduler().run(plugin, world, chunkX, chunkZ) {
                func(task)
            }
        )

        return task
    }

    override fun runLaterAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        unit: TimeUnit,
        delay: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            Bukkit.getRegionScheduler().runDelayed(plugin, world, chunkX, chunkZ, {
                func(task)
            }, TickUtil.timeToTicks(unit, delay))
        )

        return task
    }

    override fun runTimerAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        unit: TimeUnit,
        delay: Long,
        period: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            Bukkit.getRegionScheduler().runAtFixedRate(plugin, world, chunkX, chunkZ, {
                func(task)
            }, TickUtil.timeToTicks(unit, delay), TickUtil.timeToTicks(unit, period))
        )

        return task
    }
}