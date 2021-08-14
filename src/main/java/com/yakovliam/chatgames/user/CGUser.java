package com.yakovliam.chatgames.user;

import java.util.UUID;

public class CGUser {

    /**
     * UUID
     */
    private final UUID uuid;

    /**
     * Game wins
     */
    private int wins;

    /**
     * CG user
     *
     * @param uuid uuid
     * @param wins wins
     */
    public CGUser(UUID uuid, int wins) {
        this.uuid = uuid;
        this.wins = wins;
    }

    /**
     * Returns the uuid
     *
     * @return uuid
     */
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns wins
     *
     * @return wins
     */
    public int getWins() {
        return wins;
    }

    /**
     * Sets wins
     *
     * @param wins wins
     */
    public void setWins(int wins) {
        this.wins = wins;
    }
}
