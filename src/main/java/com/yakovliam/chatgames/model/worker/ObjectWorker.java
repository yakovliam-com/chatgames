package com.yakovliam.chatgames.model.worker;

public class ObjectWorker<T> implements Worker {

    /**
     * T
     */
    protected final T object;

    public ObjectWorker(T object) {
        this.object = object;
    }

    /**
     * Work
     */
    @Override
    public void work() {
    }
}
