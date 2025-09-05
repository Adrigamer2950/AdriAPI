package me.devadri.obsidian.scheduler.paper.async

import me.devadri.obsidian.scheduler.AsyncScheduler
import me.devadri.obsidian.scheduler.provider.SchedulerProvider
import me.devadri.obsidian.scheduler.task.ScheduledTask
import me.devadri.obsidian.scheduler.util.TaskEither
import me.devadri.obsidian.scheduler.util.TickUtil
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin
import java.util.concurrent.TimeUnit

class AsyncPaperScheduler(val plugin: Plugin) : AsyncScheduler {

    val syncScheduler = SchedulerProvider.Companion.create(plugin, false).sync()

    override fun run(func: (ScheduledTask) -> Unit): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
                func(task)
            })
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
            Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
                func(task)
            }, TickUtil.timeToTicks(unit, delay))
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
            Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
                func(task)
            }, TickUtil.timeToTicks(unit, delay), TickUtil.timeToTicks(unit, period))
        )

        return task
    }

    override fun runOnEntity(
        entity: Entity,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = syncScheduler.run(func)

    override fun runLaterOnEntity(
        entity: Entity,
        unit: TimeUnit,
        delay: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = syncScheduler.runLater(unit, delay, func)

    override fun runTimerOnEntity(
        entity: Entity,
        unit: TimeUnit,
        delay: Long,
        period: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = syncScheduler.runTimer(unit, delay, period, func)

    override fun runAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = syncScheduler.run(func)

    override fun runLaterAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        unit: TimeUnit,
        delay: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = syncScheduler.runLater(unit, delay, func)

    override fun runTimerAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        unit: TimeUnit,
        delay: Long,
        period: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = syncScheduler.runTimer(unit, delay, period, func)
}