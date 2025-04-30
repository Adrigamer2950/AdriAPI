package me.adrigamer2950.adriapi.api.scheduler.impl

import me.adrigamer2950.adriapi.api.scheduler.ScheduledTask
import me.adrigamer2950.adriapi.api.scheduler.Scheduler
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin
import java.util.function.Consumer

class PaperScheduler(val plugin: Plugin) : Scheduler {

    override fun run(consumer: Consumer<ScheduledTask>, async: Boolean): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getScheduler().runTaskAsynchronously(plugin, Runnable {
                    consumer.accept(t)
                })
            } else {
                Bukkit.getScheduler().runTask(plugin, Runnable {
                    consumer.accept(t)
                })
            }
        }, plugin)
    }

    override fun runLater(consumer: Consumer<ScheduledTask>, delay: Long, async: Boolean): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, Runnable {
                    consumer.accept(t)
                }, delay)
            } else {
                Bukkit.getScheduler().runTaskLater(plugin, Runnable {
                    consumer.accept(t)
                }, delay)
            }
        }, plugin)
    }

    override fun runTimer(consumer: Consumer<ScheduledTask>, delay: Long, period: Long, async: Boolean): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, Runnable {
                    consumer.accept(t)
                }, delay, period)
            } else {
                Bukkit.getScheduler().runTaskTimer(plugin, Runnable {
                    consumer.accept(t)
                }, delay, period)
            }
        }, plugin)
    }

    override fun runAsync(consumer: Consumer<ScheduledTask>): ScheduledTask {
        return run(consumer, true)
    }

    override fun runAsyncLater(consumer: Consumer<ScheduledTask>, delay: Long): ScheduledTask {
        return runLater(consumer, delay, true)
    }

    override fun runAsyncTimer(consumer: Consumer<ScheduledTask>, delay: Long, period: Long): ScheduledTask {
        return runTimer(consumer, delay, period, true)
    }

    override fun runOnEntity(consumer: Consumer<ScheduledTask>, entity: Entity, async: Boolean): ScheduledTask {
        return if (async) {
            runAsync(consumer)
        } else {
            run(consumer)
        }
    }

    override fun runLaterOnEntity(consumer: Consumer<ScheduledTask>, entity: Entity, delay: Long, async: Boolean): ScheduledTask {
        return if (async) {
            runAsyncLater(consumer, delay)
        } else {
            runLater(consumer, delay)
        }
    }

    override fun runTimerOnEntity(
        consumer: Consumer<ScheduledTask>,
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsyncTimer(consumer, delay, period)
        } else {
            runTimer(consumer, delay, period)
        }
    }

    override fun runAtRegion(
        consumer: Consumer<ScheduledTask>,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsync(consumer)
        } else {
            run(consumer)
        }
    }

    override fun runLaterAtRegion(
        consumer: Consumer<ScheduledTask>,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsyncLater(consumer, delay)
        } else {
            runLater(consumer, delay)
        }
    }

    override fun runTimerAtRegion(
        consumer: Consumer<ScheduledTask>,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        period: Long,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsyncTimer(consumer, delay, period)
        } else {
            runTimer(consumer, delay, period)
        }
    }
}
