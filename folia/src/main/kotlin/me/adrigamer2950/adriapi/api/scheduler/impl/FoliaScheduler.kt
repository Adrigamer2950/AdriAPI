package me.adrigamer2950.adriapi.api.scheduler.impl

import me.adrigamer2950.adriapi.api.scheduler.ScheduledTask
import me.adrigamer2950.adriapi.api.scheduler.Scheduler
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Entity
import org.bukkit.plugin.Plugin
import java.util.concurrent.TimeUnit

class FoliaScheduler(val plugin: Plugin) : Scheduler {

    override fun run(async: Boolean, func: (ScheduledTask) -> Unit): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getAsyncScheduler().runNow(plugin) {
                    func(t)
                }
            } else {
                Bukkit.getGlobalRegionScheduler().run(plugin) {
                    func(t)
                }
            }
        }, plugin)
    }

    override fun runAsync(func: (ScheduledTask) -> Unit): ScheduledTask {
        return run(true, func)
    }

    override fun runLater(
        delay: Long,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getAsyncScheduler().runDelayed(plugin, {
                    func(t)
                }, delay / 20, TimeUnit.SECONDS)
            } else {
                Bukkit.getGlobalRegionScheduler().runDelayed(plugin, {
                    func(t)
                }, delay)
            }
        }, plugin)
    }

    override fun runAsyncLater(delay: Long, func: (ScheduledTask) -> Unit): ScheduledTask {
        return runLater(delay, true, func)
    }

    override fun runTimer(
        delay: Long,
        period: Long,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask if (async) {
                Bukkit.getAsyncScheduler().runAtFixedRate(plugin, {
                    func(t)
                }, delay / 20, period / 20, TimeUnit.SECONDS)
            } else {
                Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, {
                    func(t)
                }, delay, period)
            }
        }, plugin)
    }

    override fun runAsyncTimer(delay: Long, period: Long, func: (ScheduledTask) -> Unit): ScheduledTask {
        return runTimer(delay, period, true, func)
    }

    override fun runOnEntity(
        entity: Entity,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask entity.scheduler.run(plugin, {
                func(t)
            }, null)!!
        },plugin)
    }

    override fun runLaterOnEntity(
        entity: Entity,
        delay: Long,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask entity.scheduler.runDelayed(plugin, {
                func(t)
            }, null, delay)!!
        }, plugin)
    }

    override fun runTimerOnEntity(
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask entity.scheduler.runAtFixedRate(plugin, {
                func(t)
            }, null, delay, period)!!
        }, plugin)
    }

    override fun runAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask Bukkit.getRegionScheduler().run(plugin, world, chunkX, chunkZ) {
                func(t)
            }
        }, plugin)
    }

    override fun runLaterAtRegion(
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        async: Boolean,
        func: (ScheduledTask) -> Unit
    ): ScheduledTask {
        return ScheduledTask({ t ->
            return@ScheduledTask Bukkit.getRegionScheduler().runDelayed(plugin, world, chunkX, chunkZ, {
                func(t)
            }, delay)
        }, plugin)
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
        return ScheduledTask({ t ->
            return@ScheduledTask Bukkit.getRegionScheduler().runAtFixedRate(plugin, world, chunkX, chunkZ, {
                func(t)
            }, delay, period)
        }, plugin)
    }
}
