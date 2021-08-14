package com.yakovliam.chatgames.config;

import com.yakovliam.chatgames.api.Plugin;
import com.yakovliam.chatgames.api.config.generic.KeyedConfiguration;
import com.yakovliam.chatgames.api.config.generic.adapter.ConfigurationAdapter;

public class ChatGamesConfig extends KeyedConfiguration {

    private final Plugin plugin;

    private final ConfigurationAdapter adapter;

    public ChatGamesConfig(Plugin plugin, ConfigurationAdapter adapter) {
        super(adapter, ChatGamesConfigKeys.getKeys());
        this.plugin = plugin;
        this.adapter = adapter;

        init();
    }

    @Override
    protected void load(boolean initial) {
        super.load(initial);
    }

    @Override
    public void reload() {
        super.reload();
    }

    public Plugin getPlugin() {
        return this.plugin;
    }

    public ConfigurationAdapter getAdapter() {
        return adapter;
    }
}