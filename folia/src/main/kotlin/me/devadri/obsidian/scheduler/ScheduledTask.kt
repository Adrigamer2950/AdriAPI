package me.devadri.obsidian.scheduler

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import io.papermc.paper.threadedregions.scheduler.ScheduledTask as FoliaScheduledTask

@Suppress("unused")
class ScheduledTask(func: (ScheduledTask) -> Any, owner: Plugin) {

    val task: Any = func(this)

    var cancelled: Boolean = false
        private set
        get() = when (task) {
            is BukkitTask -> task.isCancelled
            is FoliaScheduledTask -> task.isCancelled
            else -> true
        }

    /**
     * Cancels this task
     * @throws IllegalStateException If the task is already cancelled
     */
    @Throws(IllegalStateException::class)
    fun cancel() {
        if (cancelled) {
            throw IllegalStateException("Task is already cancelled or is null")
        }

        when (task) {
            is BukkitTask -> task.cancel()
            is FoliaScheduledTask -> task.cancel()
        }

        cancelled = true
    }
}
