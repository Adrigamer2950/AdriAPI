package me.devadri.obsidian.scheduler

import me.devadri.obsidian.scheduler.task.ScheduledTask
import java.util.concurrent.TimeUnit

interface Scheduler {

    fun run(func: (ScheduledTask) -> Unit): ScheduledTask

    fun runLater(unit: TimeUnit, delay: Long, func: (ScheduledTask) -> Unit): ScheduledTask

    fun runTimer(unit: TimeUnit, delay: Long, period: Long, func: (ScheduledTask) -> Unit): ScheduledTask
}