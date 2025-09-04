package me.devadri.obsidian.scheduler.refactor.util

import io.papermc.paper.threadedregions.scheduler.ScheduledTask
import org.bukkit.scheduler.BukkitTask
import org.jetbrains.annotations.ApiStatus

@ApiStatus.Internal
class TaskEither internal constructor(val value: Any) {

    init {
        if (!isBukkitTask && !isFoliaTask) {
            throw IllegalArgumentException("Value must be either a BukkitTask or a Folia ScheduledTask")
        }
    }

    val isBukkitTask: Boolean
        get() = value is BukkitTask

    val isFoliaTask: Boolean
        get() = value is ScheduledTask

    fun asBukkitTask(): BukkitTask {
        if (!isBukkitTask) {
            throw IllegalStateException("Value is not a BukkitTask")
        }

        return value as BukkitTask
    }

    fun asFoliaTask(): ScheduledTask {
        if (!isFoliaTask) {
            throw IllegalStateException("Value is not a Folia ScheduledTask")
        }

        return value as ScheduledTask
    }

    fun cancel() {
        when {
            isBukkitTask -> asBukkitTask().cancel()
            isFoliaTask -> asFoliaTask().cancel()
            else -> throw IllegalStateException("Value is neither a BukkitTask nor a Folia ScheduledTask")
        }
    }
}