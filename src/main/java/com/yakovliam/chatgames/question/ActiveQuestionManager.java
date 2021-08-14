package com.yakovliam.chatgames.question;

import com.yakovliam.chatgames.ChatGamesPlugin;
import com.yakovliam.chatgames.api.message.Message;
import com.yakovliam.chatgames.config.ChatGamesConfigKeys;
import com.yakovliam.chatgames.reward.Reward;
import com.yakovliam.chatgames.task.QuestionWaitExpiryTask;
import com.yakovliam.chatgames.task.RepeatingTask;
import com.yakovliam.chatgames.user.CGUser;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;
import java.util.stream.Collectors;

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
    private QuestionWaitExpiryTask waitExpiryTask;

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

    @EventHandler(priority = EventPriority.LOWEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (this.activeQuestion == null) {
            return;
        }

        String message = event.getMessage();
        if (message.equals(activeQuestion.getAnswer())) {
            // cancel
            event.setCancelled(true);
            // they got the answer!

            // cancel wait task
            this.waitExpiryTask.stop();

            String playerWonMessage = ChatGamesConfigKeys.PLAYER_WON_MESSAGE.get(plugin.getChatGamesConfig().getAdapter())
                    .replace("%player%", event.getPlayer().getName())
                    .replace("%answer%", activeQuestion.getAnswer());
            List<String> wrapper = ChatGamesConfigKeys.TEXT_WRAPPER.get(plugin.getChatGamesConfig().getAdapter()).stream()
                    .map(l -> l.replace("%text%", playerWonMessage)).collect(Collectors.toList());

            Message.Builder builder = Message.builder();
            wrapper.forEach(builder::addLine);
            builder.build().broadcast();

            // set active question to null
            this.activeQuestion = null;
            // get user
            CGUser user = plugin.getUserCache().getCache().get(event.getPlayer().getUniqueId()).join();
            // add one to wins
            user.setWins(user.getWins() + 1);

            // save user
            plugin.getStorage().saveUser(user, true);

            // give prize
            Reward reward = ChatGamesConfigKeys.RANDOM_REWARD.get(plugin.getChatGamesConfig().getAdapter());
            Bukkit.getScheduler().runTask(plugin, () -> {
                // execute command
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(event.getPlayer(), reward.getCommand()));
            });

            // send message
            String playerWonRecievedMessage = ChatGamesConfigKeys.PLAYER_WON_RECEIVED_MESSAGE.get(plugin.getChatGamesConfig().getAdapter())
                    .replace("%prize%", reward.getHandle());
            List<String> wrapper2 = ChatGamesConfigKeys.TEXT_WRAPPER.get(plugin.getChatGamesConfig().getAdapter()).stream()
                    .map(l -> l.replace("%text%", playerWonRecievedMessage)).collect(Collectors.toList());

            Message.Builder builder2 = Message.builder();
            wrapper2.forEach(builder2::addLine);
            builder2.build().broadcast();
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
        if (this.waitExpiryTask != null) {
            // cancel current
            this.waitExpiryTask.stop();
        }

        // set new question
        this.activeQuestion = plugin.getQuestionManager().pickRandom();

        // compile & send message from random question
        String text = activeQuestion.getWrapperText().replace("%supplied%", activeQuestion.getSupplied());
        List<String> wrapper = ChatGamesConfigKeys.TEXT_WRAPPER.get(plugin.getChatGamesConfig().getAdapter()).stream()
                .map(l -> l.replace("%text%", text)).collect(Collectors.toList());

        Message.Builder builder = Message.builder();
        wrapper.forEach(builder::addLine);
        builder.build().broadcast();

        // create wait task
        this.waitExpiryTask = new QuestionWaitExpiryTask(activeQuestion, plugin);
        this.waitExpiryTask.start();
    }
}
