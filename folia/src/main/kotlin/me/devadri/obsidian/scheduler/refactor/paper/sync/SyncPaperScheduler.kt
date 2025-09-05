package me.devadri.obsidian.scheduler.refactor.paper.sync

import me.devadri.obsidian.scheduler.refactor.AsyncScheduler
import me.devadri.obsidian.scheduler.refactor.task.ScheduledTask
import me.devadri.obsidian.scheduler.refactor.util.TaskEither
import me.devadri.obsidian.scheduler.refactor.util.TickUtil
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin
import java.util.concurrent.TimeUnit

class SyncPaperScheduler(val plugin: Plugin) : AsyncScheduler {

    override fun run(func: (ScheduledTask) -> Unit): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            Bukkit.getScheduler().runTask(plugin, Runnable {
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
            Bukkit.getScheduler().runTaskLater(plugin, Runnable {
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
            Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
                func(task)
            }, TickUtil.timeToTicks(unit, delay), TickUtil.timeToTicks(unit, period))
        )

        return task
    }

    override fun runOnEntity(
        entity: Entity,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = this.run(func)

    override fun runLaterOnEntity(
        entity: Entity,
        unit: TimeUnit,
        delay: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = this.runLater(unit, delay, func)

    override fun runTimerOnEntity(
        entity: Entity,
        unit: TimeUnit,
        delay: Long,
        period: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = this.runTimer(unit, delay, period, func)

    override fun runAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = this.run(func)

    override fun runLaterAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        unit: TimeUnit,
        delay: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = this.runLater(unit, delay, func)

    override fun runTimerAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        unit: TimeUnit,
        delay: Long,
        period: Long,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask = this.runTimer(unit, delay, period, func)
}