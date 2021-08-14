package com.yakovliam.chatgames.storage.implementation.json;

import com.yakovliam.chatgames.ChatGamesPlugin;
import com.yakovliam.chatgames.question.Question;
import com.yakovliam.chatgames.storage.StorageImplementation;
import com.yakovliam.chatgames.storage.implementation.json.loader.QuestionLoader;
import com.yakovliam.chatgames.user.CGUser;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class JsonStorageImplementation implements StorageImplementation {

    /**
     * Plugin
     */
    private final ChatGamesPlugin plugin;

    /**
     * Users provider
     */
    private final JsonConfigurationProvider usersProvider;

    /**
     * Question loader
     */
    private final QuestionLoader questionLoader;

    /**
     * Json storage implementation
     *
     * @param plugin plugin
     */
    public JsonStorageImplementation(ChatGamesPlugin plugin) {
        this.plugin = plugin;
        this.usersProvider = new JsonConfigurationProvider(plugin, "users.json");

        this.questionLoader = new QuestionLoader(plugin);

        // init
        init();
    }

    /**
     * Initializes
     */
    @Override
    public void init() {
        // resolves the path which creates the files if they don't already exist
        usersProvider.load();
    }

    /**
     * Shuts down
     */
    @Override
    public void shutdown() {
        // save
        save();
    }

    @Override
    public void saveUser(CGUser user) {
        ConfigurationNode node = usersProvider.getRoot().node("users");
        // get users list
        try {
            List<CGUser> userList = node.getList(CGUser.class);

            // remove if exists
            Objects.requireNonNull(userList).removeIf(u -> u.getUuid().equals(user.getUuid()));
            // add to list
            userList.add(user);
            // save to node
            node.setList(CGUser.class, userList);

            // save
            save();
        } catch (SerializationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CGUser loadUser(UUID uuid) {
        ConfigurationNode node = usersProvider.getRoot().node("users");
        // get users list
        try {
            List<CGUser> userList = node.getList(CGUser.class);
            CGUser ecoporiumUser = Objects.requireNonNull(userList).stream()
                    .filter(u -> u.getUuid().equals(uuid))
                    .findFirst()
                    .orElse(null);

            // if null, create
            if (ecoporiumUser == null) {
                ecoporiumUser = new CGUser(uuid, 0);

                // save
                saveUser(ecoporiumUser);
            }

            // return
            return ecoporiumUser;
        } catch (SerializationException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Question> loadQuestions() {
        return questionLoader.load();
    }

    /**
     * Saves the file
     */
    private void save() {
        try {
            usersProvider.getLoader().save(usersProvider.getRoot());
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the plugin
     *
     * @return plugin
     */
    @Override
    public ChatGamesPlugin getPlugin() {
        return plugin;
    }
}
