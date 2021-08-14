package com.yakovliam.chatgames.question;

import com.yakovliam.chatgames.ChatGamesPlugin;
import com.yakovliam.chatgames.api.message.Message;
import com.yakovliam.chatgames.config.ChatGamesConfigKeys;
import com.yakovliam.chatgames.task.QuestionWaitTask;
import com.yakovliam.chatgames.task.RepeatingTask;
import com.yakovliam.chatgames.user.CGUser;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ActiveQuestionManager implements Listener {

    /**
     * Chat games plugin
     */
    private final ChatGamesPlugin plugin;

    /**
     * Active question
     */
    private Question activeQuestion;

    /**
     * Wait task
     */
    private QuestionWaitTask waitTask;

    /**
     * Repeating question task
     */
    private RepeatingTask repeatingQuestionTask;

    /**
     * Active question manager
     *
     * @param plugin plugin
     */
    public ActiveQuestionManager(ChatGamesPlugin plugin) {
        this.plugin = plugin;
        // register self
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        // start task
        this.startRepeatingQuestionTask();
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        if (message.equals(activeQuestion.getAnswer())) {
            // they got the answer!

            String playerWonMessage = ChatGamesConfigKeys.PLAYER_WON_MESSAGE.get(plugin.getChatGamesConfig().getAdapter())
                    .replace("%player%", event.getPlayer().getName())
                    .replace("%answer%", activeQuestion.getAnswer());
            String wrapper = ChatGamesConfigKeys.TEXT_WRAPPER.get(plugin.getChatGamesConfig().getAdapter());

            Message.builder()
                    .addLine(wrapper.replace("%text%", playerWonMessage))
                    .build()
                    .broadcast();

            // get user
            CGUser user = plugin.getUserCache().getCache().get(event.getPlayer().getUniqueId()).join();
            // add one to wins
            user.setWins(user.getWins() + 1);

            // save user
            plugin.getStorage().saveUser(user, true);
        }
    }

    /**
     * Starts repeating question task
     */
    public void startRepeatingQuestionTask() {
        if (this.repeatingQuestionTask != null) {
            this.repeatingQuestionTask.stop();
        }

        this.repeatingQuestionTask = new RepeatingTask(plugin, ChatGamesConfigKeys.SETTINGS_DELAY_BETWEEN_GAMES_TICKS.get(plugin.getChatGamesConfig().getAdapter()), true) {

            /**
             * Run method
             * <p>
             * Implemented by children
             */
            @Override
            public void run() {
                // if there are more than 0 players on the server
                if (Bukkit.getServer().getOnlinePlayers().size() >= 1) {
                    askNewQuestion();
                }
            }
        };

        this.repeatingQuestionTask.start();
    }

    /**
     * Stops repeating question task
     */
    public void stopRepeatingQuestionTask() {
        if (this.repeatingQuestionTask != null) {
            this.repeatingQuestionTask.stop();
        }
    }

    /**
     * Asks new question
     */
    public void askNewQuestion() {
        // cancel current
        this.waitTask.stop();
        // set new question
        this.activeQuestion = plugin.getQuestionManager().pickRandom();

        // compile & send message from random question
        String text = activeQuestion.getWrapperText().replace("%question%", activeQuestion.getSupplied());
        String wrapper = ChatGamesConfigKeys.TEXT_WRAPPER.get(plugin.getChatGamesConfig().getAdapter());
        Message.builder()
                .addLine(wrapper.replace("%text%", text))
                .build()
                .broadcast();

        // create wait task
        this.waitTask = new QuestionWaitTask(activeQuestion, plugin);
        this.waitTask.start();
    }
}
