package edu.washington.ksf7.quizdroid.Models;

/**
 * Created by keegomyneego on 1/29/17.
 */

public class MultipleChoiceQuestion {

    String question;
    String[] possibleAnswers;
    int indexOfCorrectAnswer;

    public MultipleChoiceQuestion(String question, String[] possibleAnswers, int indexOfCorrectAnswer) {
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.indexOfCorrectAnswer = indexOfCorrectAnswer;
    }
}
