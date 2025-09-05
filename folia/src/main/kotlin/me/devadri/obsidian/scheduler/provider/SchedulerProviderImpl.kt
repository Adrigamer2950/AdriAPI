package me.devadri.obsidian.scheduler.provider

import me.devadri.obsidian.scheduler.AsyncScheduler
import me.devadri.obsidian.scheduler.Scheduler
import me.devadri.obsidian.scheduler.folia.async.AsyncFoliaScheduler
import me.devadri.obsidian.scheduler.folia.sync.SyncFoliaScheduler
import me.devadri.obsidian.scheduler.paper.async.AsyncPaperScheduler
import me.devadri.obsidian.scheduler.paper.sync.SyncPaperScheduler
import org.bukkit.plugin.Plugin

class SchedulerProviderImpl(val plugin: Plugin, folia: Boolean) : SchedulerProvider {

    init {
        SchedulerProvider.providers[plugin] = this
    }

    val asyncScheduler: AsyncScheduler = if (folia) {
        AsyncFoliaScheduler(plugin)
    } else {
        AsyncPaperScheduler(plugin)
    }

    val syncScheduler: Scheduler = if (folia) {
        SyncFoliaScheduler(plugin)
    } else {
        SyncPaperScheduler(plugin)
    }

    override fun async(): Scheduler = asyncScheduler

    override fun sync(): Scheduler = syncScheduler
}