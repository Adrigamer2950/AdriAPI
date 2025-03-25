package me.adrigamer2950.adriapi.api.scheduler.impl

import me.adrigamer2950.adriapi.api.scheduler.ScheduledTask
import me.adrigamer2950.adriapi.api.scheduler.Scheduler
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin

class PaperScheduler(val plugin: Plugin) : Scheduler {

    override fun run(runnable: Runnable, async: Boolean): ScheduledTask {
        return ScheduledTask(Bukkit.getScheduler().runTask(plugin, runnable), plugin)
    }

    override fun runLater(runnable: Runnable, delay: Long, async: Boolean): ScheduledTask {
        return ScheduledTask(Bukkit.getScheduler().runTaskLater(plugin, runnable, delay), plugin)
    }

    override fun runTimer(runnable: Runnable, delay: Long, period: Long, async: Boolean): ScheduledTask {
        return ScheduledTask(Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period), plugin)
    }

    override fun runAsync(runnable: Runnable): ScheduledTask {
        return ScheduledTask(Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable), plugin)
    }

    override fun runAsyncLater(runnable: Runnable, delay: Long): ScheduledTask {
        return ScheduledTask(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay), plugin)
    }

    override fun runAsyncTimer(runnable: Runnable, delay: Long, period: Long): ScheduledTask {
        return ScheduledTask(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period), plugin)
    }

    override fun runOnEntity(runnable: Runnable, entity: Entity, async: Boolean): ScheduledTask {
        return if (async) {
            runAsync(runnable)
        } else {
            run(runnable)
        }
    }

    override fun runLaterOnEntity(runnable: Runnable, entity: Entity, delay: Long, async: Boolean): ScheduledTask {
        return if (async) {
            runAsyncLater(runnable, delay)
        } else {
            runLater(runnable, delay)
        }
    }

    override fun runTimerOnEntity(
        runnable: Runnable,
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsyncTimer(runnable, delay, period)
        } else {
            runTimer(runnable, delay, period)
        }
    }

    override fun runAtRegion(
        runnable: Runnable,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsync(runnable)
        } else {
            run(runnable)
        }
    }

    override fun runLaterAtRegion(
        runnable: Runnable,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsyncLater(runnable, delay)
        } else {
            runLater(runnable, delay)
        }
    }

    override fun runTimerAtRegion(
        runnable: Runnable,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        period: Long,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsyncTimer(runnable, delay, period)
        } else {
            runTimer(runnable, delay, period)
        }
    }
}
