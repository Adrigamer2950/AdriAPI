package me.devadri.obsidian.scheduler.folia.sync

import me.devadri.obsidian.scheduler.Scheduler
import me.devadri.obsidian.scheduler.task.ScheduledTask
import me.devadri.obsidian.scheduler.util.TaskEither
import me.devadri.obsidian.scheduler.util.TickUtil
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import java.util.concurrent.TimeUnit

class SyncFoliaScheduler(val plugin: Plugin) : Scheduler {

    override fun run(func: (ScheduledTask) -> Unit): ScheduledTask {
        val task = ScheduledTask(plugin)

        task.task = TaskEither(
            Bukkit.getGlobalRegionScheduler().run(plugin) {
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
            Bukkit.getGlobalRegionScheduler().runDelayed(plugin, {
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
            Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, {
                func(task)
            }, TickUtil.timeToTicks(unit, delay), TickUtil.timeToTicks(unit, period))
        )

        return task
    }
}