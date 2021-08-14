package com.yakovliam.chatgames.storage;

import com.yakovliam.chatgames.ChatGamesPlugin;
import com.yakovliam.chatgames.question.Question;
import com.yakovliam.chatgames.user.CGUser;

import java.util.List;
import java.util.UUID;

public interface StorageImplementation {

    void init();

    void shutdown();

    void saveUser(CGUser user);

    CGUser loadUser(UUID uuid);

    List<Question> loadQuestions();

    ChatGamesPlugin getPlugin();
}
