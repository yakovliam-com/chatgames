package com.yakovliam.chatgames.task;

import com.yakovliam.chatgames.ChatGamesPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public abstract class Task implements Runnable {

    /**
     * Plugin
     */
    protected final ChatGamesPlugin plugin;

    /**
     * Current task state
     */
    private TaskState state;

    /**
     * Asynchronous?
     */
    private final boolean async;

    /**
     * The given task id
     */
    private int taskId;

    /**
     * Delay
     */
    private final long delay;

    /**
     * Task
     *
     * @param plugin     plugin
     * @param async      async
     * @param delayTicks delay, in ticks
     */
    public Task(ChatGamesPlugin plugin, boolean async, long delayTicks) {
        this.plugin = plugin;
        this.state = TaskState.IDLE;
        this.async = async;
        this.delay = delayTicks;
    }

    /**
     * Run method
     * <p>
     * Implemented by children
     */
    public abstract void run();

    /**
     * Starts the task
     */
    public void start() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();

        // if running
        if (this.state == TaskState.RUNNING) {
            // cancel self
            scheduler.cancelTask(taskId);
        }

        // register
        if (async) {
            this.taskId = scheduler.runTaskLaterAsynchronously(plugin, this, delay).getTaskId();
        } else {
            this.taskId = scheduler.runTaskLater(plugin, this, delay).getTaskId();
        }

        // set state to running
        this.state = TaskState.RUNNING;
    }

    /**
     * Stops the task
     */
    public void stop() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();

        // if running
        if (this.state == TaskState.RUNNING) {
            // cancel self
            scheduler.cancelTask(taskId);
        }
    }
}