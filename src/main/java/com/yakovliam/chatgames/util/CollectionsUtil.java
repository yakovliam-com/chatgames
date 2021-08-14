package com.yakovliam.chatgames.util;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CollectionsUtil {

    /**
     * Returns a random element
     *
     * @param items items
     * @param <T>   t items
     * @return t
     */
    public static <T> T getRandomListElement(List<T> items) {
        return items.get(ThreadLocalRandom.current().nextInt(items.size()));
    }
}
