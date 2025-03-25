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

    override fun run(runnable: Runnable, async: Boolean): ScheduledTask {
        return if (async) {
            runAsync(runnable)
        } else {
            ScheduledTask(Bukkit.getGlobalRegionScheduler().run(plugin) {
                runnable.run()
            }, plugin)
        }
    }

    override fun runAsync(runnable: Runnable): ScheduledTask {
        return ScheduledTask(Bukkit.getAsyncScheduler().runNow(plugin) {
            runnable.run()
        }, plugin)
    }

    override fun runLater(
        runnable: Runnable,
        delay: Long,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsyncLater(runnable, delay)
        } else {
            ScheduledTask(Bukkit.getGlobalRegionScheduler().runDelayed(plugin, Consumer {
                runnable.run()
            }, delay), plugin)
        }
    }

    override fun runAsyncLater(runnable: Runnable, delay: Long): ScheduledTask {
        return ScheduledTask(Bukkit.getAsyncScheduler().runDelayed(plugin, Consumer {
            runnable.run()
        }, delay / 20, TimeUnit.SECONDS), plugin)
    }

    override fun runTimer(
        runnable: Runnable,
        delay: Long,
        period: Long,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            runAsyncTimer(runnable, delay, period)
        } else {
            return ScheduledTask(Bukkit.getGlobalRegionScheduler().runAtFixedRate(plugin, Consumer {
                runnable.run()
            }, delay, period), plugin)
        }
    }

    override fun runAsyncTimer(runnable: Runnable, delay: Long, period: Long): ScheduledTask {
        return ScheduledTask(Bukkit.getAsyncScheduler().runAtFixedRate(plugin, Consumer {
            runnable.run()
        }, delay / 20, period / 20, TimeUnit.SECONDS), plugin)
    }

    override fun runOnEntity(
        runnable: Runnable,
        entity: Entity,
        async: Boolean
    ): ScheduledTask {
        return if (async) {
            ScheduledTask(Bukkit.getAsyncScheduler().runNow(plugin, Consumer {
                runnable.run()
            }), plugin)
        } else {
            return ScheduledTask(entity.scheduler.run {
                runnable.run()
            }, plugin)
        }
    }

    override fun runLaterOnEntity(
        runnable: Runnable,
        entity: Entity,
        delay: Long,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask(entity.scheduler.runDelayed(plugin, Consumer {
            runnable.run()
        }, null, delay) as Any, plugin)
    }

    override fun runTimerOnEntity(
        runnable: Runnable,
        entity: Entity,
        delay: Long,
        period: Long,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask(entity.scheduler.runAtFixedRate(plugin, Consumer {
            runnable.run()
        }, null, delay, period) as Any, plugin)
    }

    override fun runAtRegion(
        runnable: Runnable,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask(Bukkit.getRegionScheduler().run(plugin, world, chunkX, chunkZ) {
            runnable.run()
        }, plugin)
    }

    override fun runLaterAtRegion(
        runnable: Runnable,
        world: World,
        chunkX: Int,
        chunkZ: Int,
        delay: Long,
        async: Boolean
    ): ScheduledTask {
        return ScheduledTask(Bukkit.getRegionScheduler().runDelayed(plugin, world, chunkX, chunkZ, Consumer {
            runnable.run()
        }, delay) as Any, plugin)
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
        return ScheduledTask(Bukkit.getRegionScheduler().runAtFixedRate(plugin, world, chunkX, chunkZ, Consumer {
            runnable.run()
        }, delay, period) as Any, plugin)
    }
}
