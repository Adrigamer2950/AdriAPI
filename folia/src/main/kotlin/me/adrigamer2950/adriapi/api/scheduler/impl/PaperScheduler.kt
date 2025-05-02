package me.adrigamer2950.adriapi.api.scheduler.impl

import me.adrigamer2950.adriapi.api.scheduler.ScheduledTask
import me.adrigamer2950.adriapi.api.scheduler.Scheduler
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin

class PaperScheduler(val plugin: Plugin) : Scheduler {

    override fun run(async: Boolean, func: (ScheduledTask) -> Unit): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
                    func(t)
                })
            } else {
                Bukkit.getScheduler().runTask(plugin, Runnable {
                    func(t)
                })
            }
        }, plugin)
    }

    override fun runLater(delay: Long, async: Boolean, func: (ScheduledTask) -> Unit): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
                    func(t)
                }, delay)
            } else {
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    func(t)
                }, delay)
            }
        }, plugin)
    }

    override fun runTimer(delay: Long, period: Long, async: Boolean, func: (ScheduledTask) -> Unit): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
                    func(t)
                }, delay, period)
            } else {
                Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
                    func(t)
                }, delay, period)
            }
        }, plugin)
    }

    override fun runAsync(func: (ScheduledTask) -> Unit): ScheduledTask {
        return run(true, func)
    }

    override fun runAsyncLater(delay: Long, func: (ScheduledTask) -> Unit): ScheduledTask {
        return runLater(delay, true, func)
    }

    override fun runAsyncTimer(delay: Long, period: Long, func: (ScheduledTask) -> Unit): ScheduledTask {
        return runTimer(delay, period, true, func)
    }

    override fun runOnEntity(entity: Entity, async: Boolean, func: (ScheduledTask) -> Unit): ScheduledTask {
        return if (async) {
            runAsync(func)
        } else {
            run(false, func)
        }
    }

    override fun runLaterOnEntity(entity: Entity, delay: Long, async: Boolean, func: (ScheduledTask) -> Unit): ScheduledTask {
        return if (async) {
            runAsyncLater(delay, func)
        } else {
            runLater(delay, false, func)
        }
    }

    override fun runTimerOnEntity(
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return if (async) {
            runAsyncTimer(delay, period, func)
        } else {
            runTimer(delay, period, false, func)
        }
    }

    override fun runAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return if (async) {
            runAsync(func)
        } else {
            run(false, func)
        }
    }

    override fun runLaterAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return if (async) {
            runAsyncLater(delay, func)
        } else {
            runLater(delay, false, func)
        }
    }

    override fun runTimerAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        period: Long,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return if (async) {
            runAsyncTimer(delay, period, func)
        } else {
            runTimer(delay, period, false, func)
        }
    }
}
