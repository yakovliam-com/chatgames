package com.yakovliam.chatgames.listener;

import com.yakovliam.chatgames.ChatGamesPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    /**
     * Plugin
     */
    private final ChatGamesPlugin plugin;

    /**
     * Player listener
     *
     * @param plugin plugin
     */
    public PlayerListener(ChatGamesPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // load into cache
        plugin.getUserCache().getCache().get(event.getPlayer().getUniqueId());

    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // invalidate from cache
        plugin.getUserCache().getCache().synchronous().invalidate(event.getPlayer().getUniqueId());
    }
}
