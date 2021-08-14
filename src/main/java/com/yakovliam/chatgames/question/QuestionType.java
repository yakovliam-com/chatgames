package com.yakovliam.chatgames.question;

import java.util.concurrent.ThreadLocalRandom;

public enum QuestionType {

    UNSCRAMBLE,
    MATH;

    public static QuestionType random() {
        int x = ThreadLocalRandom.current().nextInt(QuestionType.values().length);
        return QuestionType.values()[x];
    }
}
