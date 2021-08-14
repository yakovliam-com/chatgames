package com.yakovliam.chatgames.storage.implementation.json.loader;

public class MathEquation {

    /**
     * Supplied
     */
    private final String supplied;

    /**
     * Answer
     */
    private final String answer;

    /**
     * Math equation
     *
     * @param supplied supplied
     * @param answer   answer
     */
    public MathEquation(String supplied, String answer) {
        this.supplied = supplied;
        this.answer = answer;
    }

    /**
     * Returns supplied
     *
     * @return supplied
     */
    public String getSupplied() {
        return supplied;
    }

    /**
     * Returns answer
     *
     * @return answer
     */
    public String getAnswer() {
        return answer;
    }
}
