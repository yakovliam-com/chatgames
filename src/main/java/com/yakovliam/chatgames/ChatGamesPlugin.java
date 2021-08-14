package com.yakovliam.chatgames;

import com.yakovliam.chatgames.api.Plugin;
import com.yakovliam.chatgames.api.message.Message;
import com.yakovliam.chatgames.command.CommandManager;
import com.yakovliam.chatgames.config.ChatGamesConfig;
import com.yakovliam.chatgames.listener.PlayerListener;
import com.yakovliam.chatgames.question.ActiveQuestionManager;
import com.yakovliam.chatgames.question.QuestionManager;
import com.yakovliam.chatgames.storage.Storage;
import com.yakovliam.chatgames.storage.implementation.json.JsonStorageImplementation;
import com.yakovliam.chatgames.user.UserCache;

public class ChatGamesPlugin extends Plugin {

    /**
     * Storage
     */
    private Storage storage;

    /**
     * Chat games config
     */
    private ChatGamesConfig chatGamesConfig;

    /**
     * User cache
     */
    private UserCache userCache;

    /**
     * Question manager
     */
    private QuestionManager questionManager;

    /**
     * Active question manager
     */
    private ActiveQuestionManager activeQuestionManager;

    @Override
    public void onEnable() {
        Message.initAudience(this);

        this.chatGamesConfig = new ChatGamesConfig(this, provideConfigAdapter("config.yml"));

        this.loadStorage();
        this.loadQuestionManager();

        this.userCache = new UserCache(this);

        this.getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        this.activeQuestionManager = new ActiveQuestionManager(this);

        new CommandManager(this);
    }

    @Override
    public void onDisable() {
        // save users
        this.userCache.getCache().synchronous().asMap().values()
                .forEach(u -> this.getStorage().saveUser(u, false));
    }

    /**
     * Returns storage
     *
     * @return storage
     */
    public Storage getStorage() {
        return storage;
    }

    /**
     * Returns user cache
     *
     * @return user cache
     */
    public UserCache getUserCache() {
        return userCache;
    }

    /**
     * Returns chat games config
     *
     * @return chat games config
     */
    public ChatGamesConfig getChatGamesConfig() {
        return chatGamesConfig;
    }

    /**
     * Returns question manager
     *
     * @return question manager
     */
    public QuestionManager getQuestionManager() {
        return questionManager;
    }

    /**
     * Returns active question manager
     *
     * @return active question manager
     */
    public ActiveQuestionManager getActiveQuestionManager() {
        return activeQuestionManager;
    }

    /**
     * Loads question manager
     */
    public void loadQuestionManager() {
        this.questionManager = new QuestionManager(this);
    }

    /**
     * Loads storage
     */
    public void loadStorage() {
        if (this.storage != null) {
            this.storage.getStorageImplementation().shutdown();
        }
        this.storage = new Storage(new JsonStorageImplementation(this));
    }
}
