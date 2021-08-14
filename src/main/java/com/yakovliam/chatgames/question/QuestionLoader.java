package com.yakovliam.chatgames.question;

import com.yakovliam.chatgames.ChatGamesPlugin;

import java.util.UUID;

public class QuestionLoader {

    /**
     * Chat games plugin
     */
    private final ChatGamesPlugin plugin;

    /**
     * Question manager
     */
    private final QuestionManager questionManager;

    /**
     * Question loader
     *
     * @param plugin plugin
     * @param questionManager question manager
     */
    public QuestionLoader(ChatGamesPlugin plugin, QuestionManager questionManager) {
        this.plugin = plugin;
        this.questionManager = questionManager;
    }

    /**
     * Loads into manager from storage
     */
    public void load() {
        // get list from storage & load into manager
        plugin.getStorage().loadQuestions().forEach(q -> questionManager.put(UUID.randomUUID(), q));
    }
}
