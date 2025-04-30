package me.adrigamer2950.adriapi.api.scheduler

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import io.papermc.paper.threadedregions.scheduler.ScheduledTask as FoliaScheduledTask

@Suppress("unused")
class ScheduledTask(var task: Any? = null, val owner: Plugin) {

    constructor(task: (ScheduledTask) -> Any, owner: Plugin) : this(null, owner) {
        this.task = task.invoke(this)
    }

    var cancelled: Boolean = false
        private set
        get() = when (task) {
            is BukkitTask -> (task as BukkitTask).isCancelled
            is FoliaScheduledTask -> (task as FoliaScheduledTask).isCancelled
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

        (task as? BukkitTask)?.cancel()
        (task as? FoliaScheduledTask)?.cancel()

        cancelled = true
    }
}
