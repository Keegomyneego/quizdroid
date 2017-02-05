package edu.washington.ksf7.quizdroid.Models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by keegomyneego on 1/29/17.
 */

public class Quiz {

    private final String topic;
    private final List<MultipleChoiceQuestion> questions;

    public Quiz(String topic, MultipleChoiceQuestion[] questions) {
        this.topic = topic;
        this.questions = new ArrayList<>(Arrays.asList(questions));
    }

    // Getters

    public String getTopic() {
        return topic;
    }

    public List<MultipleChoiceQuestion> getQuestions() {
        return questions;
    }

    public String getPossibleAnswer(int questionNumber, int answerNumber) {
        return questions.get(questionNumber).possibleAnswers[answerNumber];
    }

    public String getCorrectAnswer(int questionNumber) {
        MultipleChoiceQuestion question = questions.get(questionNumber);

        return question.possibleAnswers[question.indexOfCorrectAnswer];
    }

    // Methods

    public boolean guessIsCorrect(int questionNumber, int guess) {
        int correctAnswer = questions.get(questionNumber).indexOfCorrectAnswer;

        return guess == correctAnswer;
    }
}
