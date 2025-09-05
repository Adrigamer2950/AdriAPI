package me.devadri.obsidian.scheduler.refactor.task

import me.devadri.obsidian.scheduler.refactor.util.TaskEither
import org.bukkit.plugin.Plugin

class ScheduledTask(val owner: Plugin) {

    lateinit var task: TaskEither
        internal set

    var cancelled: Boolean = false
        private set
        get() = when {
            task.isBukkitTask -> task.asBukkitTask().isCancelled
            task.isFoliaTask -> task.asFoliaTask().isCancelled
            else -> field
        }

    init {
        tasks.add(this)
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

        task.cancel()

        cancelled = true

        tasks.remove(this)
    }

    companion object {
        private val tasks: MutableSet<ScheduledTask> = mutableSetOf()

        @JvmStatic
        fun cancelAll(plugin: Plugin) {
            tasks.filter { it.owner == plugin }.forEach { it.cancel() }
        }
    }
}