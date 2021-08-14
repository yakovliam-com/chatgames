package com.yakovliam.chatgames.user;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.yakovliam.chatgames.ChatGamesPlugin;
import com.yakovliam.chatgames.model.cache.AsyncCache;

import java.util.UUID;

public class UserCache extends AsyncCache<UUID, CGUser> {

    /**
     * Cache
     *
     * @param plugin plugin
     */
    public UserCache(ChatGamesPlugin plugin) {
        super(Caffeine.newBuilder()
                .buildAsync(uuid -> plugin.getStorage().loadUser(uuid)));
    }
}
