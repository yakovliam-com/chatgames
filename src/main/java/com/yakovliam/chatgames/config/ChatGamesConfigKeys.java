package com.yakovliam.chatgames.config;

import com.yakovliam.chatgames.api.config.generic.KeyedConfiguration;
import com.yakovliam.chatgames.api.config.generic.key.ConfigKey;
import com.yakovliam.chatgames.api.config.generic.key.SimpleConfigKey;
import com.yakovliam.chatgames.api.message.Message;
import sh.okx.timeapi.TimeAPI;

import java.util.List;

import static com.yakovliam.chatgames.api.config.generic.key.ConfigKeyFactory.key;

public class ChatGamesConfigKeys {

    public static ConfigKey<Long> SETTINGS_DELAY_BETWEEN_GAMES_TICKS = key(c -> {
        // get string
        String delay = c.getString("settings.delay-between-games", "");
        // parse
        return (new TimeAPI(delay).getMilliseconds() * 1000) * 20;
    });

    public static ConfigKey<Long> SETTINGS_WAIT_UNTIL_ANSWER_FROM_ASK = key(c -> {
        // get string
        String delay = c.getString("settings.wait-until-answer-from-ask", "");
        // parse
        return (new TimeAPI(delay).getMilliseconds() * 1000) * 20;
    });

    public static ConfigKey<String> PLAYER_WON_MESSAGE = key(c -> c.getString("player-won-message", ""));

    public static ConfigKey<String> NOBODY_WON_MESSAGE = key(c -> c.getString("nobody-won-message", ""));
    public static ConfigKey<String> TEXT_WRAPPER = key(c -> c.getString("text-wrapper", ""));

    public static ConfigKey<String> QUESTION_TYPE_MESSAGE_UNSCRAMBLE = key(c -> c.getString("question-type-message.unscramble", ""));

    public static ConfigKey<String> QUESTION_TYPE_MESSAGE_MATH = key(c -> c.getString("question-type-message.math", ""));

    private static final List<SimpleConfigKey<?>> KEYS = KeyedConfiguration.initialise(ChatGamesConfigKeys.class);

    public static List<? extends ConfigKey<?>> getKeys() {
        return KEYS;
    }
}
