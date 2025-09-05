package me.devadri.obsidian.scheduler.refactor.provider

import me.devadri.obsidian.scheduler.refactor.Scheduler
import org.bukkit.plugin.Plugin

class SchedulerProviderImpl(val plugin: Plugin, folia: Boolean) : SchedulerProvider {

    init {
        SchedulerProvider.providers[plugin] = this
    }

    val asyncScheduler: Scheduler = if (folia) {
        TODO("FoliaScheduler not yet implemented")
    } else {
        TODO("PaperScheduler not yet implemented")
    }

    val syncScheduler: Scheduler = if (folia) {
        TODO("FoliaScheduler not yet implemented")
    } else {
        TODO("PaperScheduler not yet implemented")
    }

    override fun async(): Scheduler = asyncScheduler

    override fun sync(): Scheduler = syncScheduler
}