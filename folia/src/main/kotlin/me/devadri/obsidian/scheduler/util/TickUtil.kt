package me.devadri.obsidian.scheduler.util

import java.util.concurrent.TimeUnit

object TickUtil {

    @JvmStatic
    fun timeToTicks(unit: TimeUnit, time: Long): Long = when (unit) {
        TimeUnit.NANOSECONDS -> time / 50_000
        TimeUnit.MICROSECONDS -> time / 50
        TimeUnit.MILLISECONDS -> time / 50
        TimeUnit.SECONDS -> time * 20
        TimeUnit.MINUTES -> time * 20 * 60
        TimeUnit.HOURS -> time * 20 * 60 * 60
        TimeUnit.DAYS -> time * 20 * 60 * 60 * 24
    }
}