package me.adrigamer2950.adriapi.api.scheduler

import me.adrigamer2950.adriapi.api.scheduler.impl.FoliaScheduler
import me.adrigamer2950.adriapi.api.scheduler.impl.PaperScheduler
import org.bukkit.plugin.Plugin

object SchedulerFactory {

    @JvmOverloads
    @JvmStatic
    fun make(plugin: Plugin, folia: Boolean = false): Scheduler {
        return if (folia) {
            FoliaScheduler(plugin)
        } else {
            PaperScheduler(plugin)
        }
    }
}