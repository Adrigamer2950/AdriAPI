package me.devadri.obsidian.scheduler.refactor

import me.devadri.obsidian.scheduler.refactor.task.ScheduledTask
import org.bukkit.World
import org.bukkit.entity.Entity
import java.util.concurrent.TimeUnit

interface AsyncScheduler : Scheduler {

    fun runOnEntity(entity: Entity, func: (ScheduledTask) -> Unit): ScheduledTask

    fun runLaterOnEntity(entity: Entity, unit: TimeUnit, delay: Long, func: (ScheduledTask) -> Unit): ScheduledTask

    fun runTimerOnEntity(entity: Entity, unit: TimeUnit, delay: Long, period: Long, func: (ScheduledTask) -> Unit): ScheduledTask

    fun runAtRegion(world: World, chunkX: Int, chunkZ: Int, func: (ScheduledTask) -> Unit): ScheduledTask

    fun runLaterAtRegion(world: World, chunkX: Int, chunkZ: Int, unit: TimeUnit, delay: Long, func: (ScheduledTask) -> Unit): ScheduledTask

    fun runTimerAtRegion(world: World, chunkX: Int, chunkZ: Int, unit: TimeUnit, delay: Long, period: Long, func: (ScheduledTask) -> Unit): ScheduledTask
}