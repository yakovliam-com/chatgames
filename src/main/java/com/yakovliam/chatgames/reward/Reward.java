package com.yakovliam.chatgames.reward;

public class Reward {

    /**
     * Handle
     */
    private final String handle;

    /**
     * Chance
     */
    private final double chance;

    /**
     * Command
     */
    private final String command;

    /**
     * Reward
     *
     * @param handle  handle
     * @param chance  chance
     * @param command command
     */
    public Reward(String handle, double chance, String command) {
        this.handle = handle;
        this.chance = chance;
        this.command = command;
    }

    /**
     * Return handle
     *
     * @return handle
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Return chance
     *
     * @return chance
     */
    public double getChance() {
        return chance;
    }

    /**
     * Return command
     *
     * @return command
     */
    public String getCommand() {
        return command;
    }
}
