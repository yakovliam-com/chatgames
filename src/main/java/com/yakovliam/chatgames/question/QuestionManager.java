package com.yakovliam.chatgames.question;

import com.yakovliam.chatgames.ChatGamesPlugin;
import com.yakovliam.chatgames.model.manager.MapManager;
import com.yakovliam.chatgames.util.CollectionsUtil;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class QuestionManager extends MapManager<UUID, Question> {

    /**
     * Question manager
     *
     * @param plugin plugin
     */
    public QuestionManager(ChatGamesPlugin plugin) {
        super(new HashMap<>());

        // load into storage
        new QuestionLoader(plugin).load();
    }

    /**
     * Picks random question
     *
     * @return question
     */
    public Question pickRandom() {
        List<Question> questions = this.map.values().stream().toList();

        return CollectionsUtil.getRandomListElement(questions);
    }
}
