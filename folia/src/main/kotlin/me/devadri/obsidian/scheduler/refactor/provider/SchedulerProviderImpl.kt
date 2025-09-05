package me.devadri.obsidian.scheduler.refactor.provider

import me.devadri.obsidian.scheduler.refactor.AsyncScheduler
import me.devadri.obsidian.scheduler.refactor.Scheduler
import me.devadri.obsidian.scheduler.refactor.folia.async.AsyncFoliaScheduler
import me.devadri.obsidian.scheduler.refactor.folia.sync.SyncFoliaScheduler
import me.devadri.obsidian.scheduler.refactor.paper.async.AsyncPaperScheduler
import me.devadri.obsidian.scheduler.refactor.paper.sync.SyncPaperScheduler
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