package me.adrigamer2950.adriapi.api.scheduler.impl

import me.adrigamer2950.adriapi.api.scheduler.ScheduledTask
import me.adrigamer2950.adriapi.api.scheduler.Scheduler
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin
import java.util.concurrent.TimeUnit
import java.util.function.Consumer

class FoliaScheduler(val plugin: Plugin) : Scheduler {

    override fun run(consumer: Consumer<ScheduledTask>, async: Boolean): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getAsyncScheduler().runNow(plugin) {
                    consumer.accept(t)
                }
            } else {
                Bukkit.getGlobalRegionScheduler().run(plugin) {
                    consumer.accept(t)
                }
            }
        }, plugin)
    }

    override fun runAsync(consumer: Consumer<ScheduledTask>): ScheduledTask {
        return run(consumer, true)
    }

    override fun runLater(
        consumer: Consumer<ScheduledTask>,
        delay: Long,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getAsyncScheduler().runDelayed(plugin, {
                    consumer.accept(t)
                }, delay / 20, TimeUnit.SECONDS)
            } else {
                Bukkit.getGlobalRegionScheduler().runDelayed(plugin, {
                    consumer.accept(t)
                }, delay)
            }
        }, plugin)
    }

    override fun runAsyncLater(consumer: Consumer<ScheduledTask>, delay: Long): ScheduledTask {
        return runLater(consumer, delay, true)
    }

    override fun runTimer(
        consumer: Consumer<ScheduledTask>,
        delay: Long,
        period: Long,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
                    consumer.accept(t)
                }, delay / 20, period / 20, TimeUnit.SECONDS)
            } else {
                Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, {
                    consumer.accept(t)
                }, delay, period)
            }
        }, plugin)
    }

    override fun runAsyncTimer(consumer: Consumer<ScheduledTask>, delay: Long, period: Long): ScheduledTask {
        return runTimer(consumer, delay, period, true)
    }

    override fun runOnEntity(
        consumer: Consumer<ScheduledTask>,
        entity: Entity,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask entity.scheduler.run(plugin, {
                consumer.accept(t)
            }, null)!!
        },plugin)
    }

    override fun runLaterOnEntity(
        consumer: Consumer<ScheduledTask>,
        entity: Entity,
        delay: Long,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask entity.scheduler.runDelayed(plugin, {
                consumer.accept(t)
            }, null, delay)!!
        }, plugin)
    }

    override fun runTimerOnEntity(
        consumer: Consumer<ScheduledTask>,
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask entity.scheduler.runAtFixedRate(plugin, {
                consumer.accept(t)
            }, null, delay, period)!!
        }, plugin)
    }

    override fun runAtRegion(
        consumer: Consumer<ScheduledTask>,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask Bukkit.getRegionScheduler().run(plugin, world, chunkX, chunkZ) {
                consumer.accept(t)
            }
        }, plugin)
    }

    override fun runLaterAtRegion(
        consumer: Consumer<ScheduledTask>,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask Bukkit.getRegionScheduler().runDelayed(plugin, world, chunkX, chunkZ, {
                consumer.accept(t)
            }, delay)
        }, plugin)
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
        return ScheduledTask({ t ->
            return@ScheduledTask Bukkit.getRegionScheduler().runAtFixedRate(plugin, world, chunkX, chunkZ, {
                consumer.accept(t)
            }, delay, period)
        }, plugin)
    }
}
