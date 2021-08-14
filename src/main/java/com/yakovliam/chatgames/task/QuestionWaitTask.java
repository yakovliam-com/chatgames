package com.yakovliam.chatgames.task;

import com.yakovliam.chatgames.ChatGamesPlugin;
import com.yakovliam.chatgames.api.message.Message;
import com.yakovliam.chatgames.config.ChatGamesConfigKeys;
import com.yakovliam.chatgames.question.Question;

public class QuestionWaitTask extends Task {

    /**
     * Question
     */
    private final Question question;

    /**
     * Plugin
     */
    private final ChatGamesPlugin plugin;

    /**
     * Questions wait task
     *
     * @param question question
     * @param plugin   plugin
     */
    public QuestionWaitTask(Question question, ChatGamesPlugin plugin) {
        super(plugin, false, ChatGamesConfigKeys.SETTINGS_WAIT_UNTIL_ANSWER_FROM_ASK.get(plugin.getChatGamesConfig().getAdapter()));
        this.question = question;
        this.plugin = plugin;
    }

    /**
     * Run method
     * <p>
     * Implemented by children
     */
    @Override
    public void run() {
        // the question expired, so send a message "nobody got the answer! it was %answer%!"

        String playerWonMessage = ChatGamesConfigKeys.NOBODY_WON_MESSAGE.get(plugin.getChatGamesConfig().getAdapter())
                .replace("%answer%", question.getAnswer());
        String wrapper = ChatGamesConfigKeys.TEXT_WRAPPER.get(plugin.getChatGamesConfig().getAdapter());

        Message.builder()
                .addLine(wrapper.replace("%text%", playerWonMessage))
                .build()
                .broadcast();

        // use active question manager to queue a new question
        plugin.getActiveQuestionManager().askNewQuestion();
    }
}
