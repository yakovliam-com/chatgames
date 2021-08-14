package com.yakovliam.chatgames.question;

import com.yakovliam.chatgames.ChatGamesPlugin;

import java.util.UUID;

public class QuestionLoader {

    /**
     * Chat games plugin
     */
    private final ChatGamesPlugin plugin;

    /**
     * Question loader
     *
     * @param plugin plugin
     */
    public QuestionLoader(ChatGamesPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Loads into manager from storage
     */
    public void load() {
        // get list from storage & load into manager
        plugin.getStorage().loadQuestions().forEach(q -> plugin.getQuestionManager().put(UUID.randomUUID(), q));
    }
}
