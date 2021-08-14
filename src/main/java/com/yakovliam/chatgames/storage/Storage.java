package com.yakovliam.chatgames.storage;

import com.yakovliam.chatgames.question.Question;
import com.yakovliam.chatgames.user.CGUser;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class Storage {

    /**
     * Storage implementation
     */
    private final StorageImplementation storageImplementation;

    /**
     * Storage
     *
     * @param storageImplementation storage implementation
     */
    public Storage(StorageImplementation storageImplementation) {
        this.storageImplementation = storageImplementation;
    }

    /**
     * Returns the storage implementation
     *
     * @return implementation
     */
    public StorageImplementation getStorageImplementation() {
        return storageImplementation;
    }

    /**
     * Saves a user
     *
     * @param user user
     */
    public void saveUser(CGUser user, boolean asynchronous) {
        if (asynchronous) {
            CompletableFuture.runAsync(() -> this.storageImplementation.saveUser(user));
        } else {
            this.storageImplementation.saveUser(user);
        }
    }

    /**
     * Loads a user
     *
     * @param uuid user
     */
    public CGUser loadUser(UUID uuid) {
        return this.storageImplementation.loadUser(uuid);
    }

    /**
     * Loads questions
     *
     * @return questions
     */
    public List<Question> loadQuestions() {
        return this.storageImplementation.loadQuestions();
    }
}
