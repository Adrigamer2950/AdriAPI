package me.adrigamer2950.adriapi.api.scheduler.task;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

@RequiredArgsConstructor
public final class ScheduledTask {

    @Getter
    private boolean cancelled = false;
    private final Object task;

    @Getter
    private final Plugin owner;

    /**
     * Cancels this task
     * @throws IllegalStateException If the task is already cancelled
     */
    public void cancel() {
        if (cancelled) {
            throw new IllegalStateException("Task is already cancelled");
        }

        if (task instanceof BukkitTask) {
            ((BukkitTask) task).cancel();
        } else if (task instanceof io.papermc.paper.threadedregions.scheduler.ScheduledTask) {
            ((io.papermc.paper.threadedregions.scheduler.ScheduledTask) task).cancel();
        }

        cancelled = true;
    }
}
