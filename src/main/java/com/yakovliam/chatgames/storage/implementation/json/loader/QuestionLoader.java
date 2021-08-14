package com.yakovliam.chatgames.storage.implementation.json.loader;

import com.yakovliam.chatgames.ChatGamesPlugin;
import com.yakovliam.chatgames.config.ChatGamesConfigKeys;
import com.yakovliam.chatgames.question.Question;
import com.yakovliam.chatgames.question.QuestionType;
import com.yakovliam.chatgames.storage.implementation.json.JsonConfigurationProvider;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionLoader {

    /**
     * Math equations provider
     */
    private final JsonConfigurationProvider mathEquationsProvider;

    /**
     * Unscramble words provider
     */
    private final JsonConfigurationProvider unscrambleWordsProvider;

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
        this.mathEquationsProvider = new JsonConfigurationProvider(plugin, "math-equations.json");
        this.unscrambleWordsProvider = new JsonConfigurationProvider(plugin, "unscramble-words.json");
        this.plugin = plugin;
    }

    /**
     * Loads questions
     *
     * @return question
     */
    public List<Question> load() {
        List<Question> questions = new ArrayList<>();

        questions.addAll(loadMathEquations());
        questions.addAll(loadUnscrambles());

        return questions;
    }

    /**
     * Loads maths equations
     *
     * @return math equations
     */
    private List<Question> loadMathEquations() {
        try {
            List<MathEquation> mathEquations = mathEquationsProvider.getRoot().getList(MathEquation.class);
            // convert to question
            return mathEquations.stream()
                    .map(e -> new Question(QuestionType.MATH, ChatGamesConfigKeys.QUESTION_TYPE_MESSAGE_MATH.get(plugin.getChatGamesConfig().getAdapter()),
                            e.getSupplied(), e.getAnswer())).collect(Collectors.toList());
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    /**
     * Loads unscrambles
     *
     * @return unscrambles
     */
    private List<Question> loadUnscrambles() {
        try {
            List<String> unscrambleWords = unscrambleWordsProvider.getRoot().getList(String.class);
            // convert to question
            return unscrambleWords.stream()
                    .map(s -> new Question(QuestionType.UNSCRAMBLE, ChatGamesConfigKeys.QUESTION_TYPE_MESSAGE_UNSCRAMBLE.get(plugin.getChatGamesConfig().getAdapter()),
                            s, s)).collect(Collectors.toList());
        } catch (SerializationException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
