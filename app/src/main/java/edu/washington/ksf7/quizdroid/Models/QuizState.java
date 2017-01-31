package edu.washington.ksf7.quizdroid.Models;

import java.util.List;

/**
 * Created by keegomyneego on 1/30/17.
 */

public class QuizState {

    public static QuizState currentState;

    // Immutable quiz info
    private final Quiz currentQuiz;
    private final int numberOfQuestions;

    // Mutable question info
    private int currentQuestionNumber;
    private int[] guesses;

    private QuizState(Quiz quiz) {
        currentQuiz = quiz;
        numberOfQuestions = quiz.getQuestions().size();
        currentQuestionNumber = 0;
        guesses = new int[numberOfQuestions];

        for (int i = 0; i < numberOfQuestions; i++) {
            guesses[i] = -1;
        }
    }

    public static void startQuiz(Quiz quiz) {
        currentState = new QuizState(quiz);
    }

    public Quiz getCurrentQuiz() {
        return currentQuiz;
    }

    public MultipleChoiceQuestion getCurrentQuestion() {
        return currentQuiz.getQuestions().get(currentQuestionNumber);
    }

    public String getPossibleAnswer(int index) {
        return getCurrentQuestion().possibleAnswers[index];
    }

    public String getCorrectAnswer() {
        MultipleChoiceQuestion currentQuestion = getCurrentQuestion();

        return currentQuestion.possibleAnswers[currentQuestion.indexOfCorrectAnswer];
    }

    public String getCurrentGuess() {
        int currentGuess = guesses[currentQuestionNumber];

        if (currentGuess == -1) {
            return null;
        }

        return getPossibleAnswer(currentGuess);
    }

    public int getCurrentScore() {
        int score = 0;
        List<MultipleChoiceQuestion> questions = currentQuiz.getQuestions();

        for (int i = 0; i <= currentQuestionNumber; i++) {
            if (guesses[i] == questions.get(i).indexOfCorrectAnswer) {
                score++;
            }
        }

        return score;
    }

    public int getGuessesMadeSoFar() {
        return currentQuestionNumber + 1;
    }

    public void guessAnswer(int index) {
        if (index < numberOfQuestions) {
            guesses[currentQuestionNumber] = index;
        }
    }

    public void moveToNextQuestion() {
        int indexOfNextQuestion = currentQuestionNumber + 1;

        // upper bound that shit
        currentQuestionNumber = Math.max(indexOfNextQuestion, numberOfQuestions - 1);
    }

    public boolean isLastQuestion() {
        return currentQuestionNumber < numberOfQuestions;
    }

    public boolean guessWasCorrect() {
        int lastGuessedAnswer = guesses[currentQuestionNumber];
        int correctAnswer = currentQuiz.getQuestions().get(currentQuestionNumber).indexOfCorrectAnswer;

        return lastGuessedAnswer == correctAnswer;
    }
}
