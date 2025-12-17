package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuizQuestion implements Serializable {
    private static final long serialVersionUID = 1L;

    private String question;
    private ArrayList<String> options;
    private int correctIndex; // 0-based

    public QuizQuestion(String question, List<String> options, int correctIndex) {
        this.question = question;
        this.options = options == null ? new ArrayList<>() : new ArrayList<>(options);
        this.correctIndex = Math.max(0, Math.min(2, correctIndex));
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return new ArrayList<>(options);
    }

    public int getCorrectIndex() {
        return correctIndex;
    }
}
