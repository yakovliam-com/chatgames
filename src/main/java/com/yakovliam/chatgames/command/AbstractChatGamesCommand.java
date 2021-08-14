package com.yakovliam.chatgames.command;

import co.aikar.commands.BaseCommand;
import com.yakovliam.chatgames.ChatGamesPlugin;

public abstract class AbstractChatGamesCommand extends BaseCommand {

    /**
     * Plugin
     */
    protected final ChatGamesPlugin plugin;

    /**
     * Manager
     */
    protected final CommandManager manager;

    /**
     * Chat games command
     *
     * @param manager manager
     * @param plugin  plugin
     */
    public AbstractChatGamesCommand(CommandManager manager, ChatGamesPlugin plugin) {
        this.plugin = plugin;
        this.manager = manager;
    }

    protected abstract void registerCompletions();

    protected abstract void registerContexts();
}
