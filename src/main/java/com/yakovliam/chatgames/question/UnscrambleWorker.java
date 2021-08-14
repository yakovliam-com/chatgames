package com.yakovliam.chatgames.question;

import com.yakovliam.chatgames.model.worker.ObjectWorker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UnscrambleWorker extends ObjectWorker<Question> {

    /**
     * Worker
     *
     * @param question question
     */
    public UnscrambleWorker(Question question) {
        super(question);
    }

    /**
     * Work
     */
    @Override
    public void work() {
        object.setSupplied(scramble(object.getSupplied()));
    }

    /**
     * Scrambles a string
     *
     * @param s s
     * @return string, scrambled
     */
    private String scramble(String s) {
        List<Character> list = new ArrayList<Character>();
        for(char c : s.toCharArray()) {
            list.add(c);
        }
        Collections.shuffle(list);
        StringBuilder builder = new StringBuilder();
        for(char c : list) {
            builder.append(c);
        }

        return builder.toString();
    }
}
