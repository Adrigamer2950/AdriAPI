package me.adrigamer2950.adriapi.api.scheduler

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitTask
import io.papermc.paper.threadedregions.scheduler.ScheduledTask as FoliaScheduledTask

open class ScheduledTask(val task: Any, val owner: Plugin) {

    var cancelled: Boolean = false
        private set

    /**
     * Cancels this task
     */
    @Throws(IllegalStateException::class)
    fun cancel() {
        if (cancelled) {
            throw IllegalStateException ("Task is already cancelled");
        }

        if (task is BukkitTask) {
            task.cancel();
        } else if (task is FoliaScheduledTask) {
            task.cancel();
        }

        cancelled = true;
    }
}
