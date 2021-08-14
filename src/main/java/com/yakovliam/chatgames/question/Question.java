package com.yakovliam.chatgames.question;

public class Question {

    /**
     * Question type
     */
    private final QuestionType type;

    /**
     * The wrapper text for the question
     * <p>
     * Example: "What is %question%?", "Unscramble %question% to win a prize!"
     * Everything except the %question% placeholder
     */
    private final String wrapperText;

    /**
     * The question supplied text itself
     * <p>
     * Example: "5 + 3", "olhel"
     */
    private String supplied;

    /**
     * The answer to the question
     */
    private final String answer;

    /**
     * Question
     *
     * @param type        type
     * @param wrapperText wrapper text
     * @param supplied    supplied
     * @param answer      answer
     */
    public Question(QuestionType type, String wrapperText, String supplied, String answer) {
        this.type = type;
        this.wrapperText = wrapperText;
        this.supplied = supplied;
        this.answer = answer;

        // run worker
        executeApplicableWorker();
    }

    /**
     * Returns the wrapper text
     *
     * @return wrapper text
     */
    public String getWrapperText() {
        return wrapperText;
    }

    /**
     * Returns the question
     *
     * @return question
     */
    public String getSupplied() {
        return supplied;
    }

    /**
     * Sets the supplied string
     *
     * @param supplied supplied
     */
    public void setSupplied(String supplied) {
        this.supplied = supplied;
    }

    /**
     * Returns the answer
     *
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Returns the question type
     *
     * @return type
     */
    public QuestionType getType() {
        return type;
    }

    /**
     * Does a task
     */
    public void executeApplicableWorker() {
        if (type == QuestionType.UNSCRAMBLE) {
            new UnscrambleWorker(this).work();
        }
    }
}
