package me.devadri.obsidian.scheduler.provider

import me.devadri.obsidian.scheduler.AsyncScheduler
import me.devadri.obsidian.scheduler.Scheduler
import org.bukkit.plugin.Plugin

interface SchedulerProvider {

    fun async(): AsyncScheduler

    /**
     * In Folia, sync tasks are ran through Folia's Global Region Scheduler
     */
    fun sync(): Scheduler

    companion object {
        internal val providers: MutableMap<Plugin, SchedulerProvider> = mutableMapOf()

        @JvmStatic
        fun create(plugin: Plugin, folia: Boolean): SchedulerProvider {
            return providers[plugin] ?: SchedulerProviderImpl(plugin, folia)
        }
    }
}