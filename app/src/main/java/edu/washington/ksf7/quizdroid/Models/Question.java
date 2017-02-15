package edu.washington.ksf7.quizdroid.Models;

/**
 * Created by keegomyneego on 1/29/17.
 */

public class Question {

    public final String question;
    public final String[] possibleAnswers;
    public final int indexOfCorrectAnswer;

    public Question(String question, String[] possibleAnswers, int indexOfCorrectAnswer) {
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.indexOfCorrectAnswer = indexOfCorrectAnswer;
    }
}
