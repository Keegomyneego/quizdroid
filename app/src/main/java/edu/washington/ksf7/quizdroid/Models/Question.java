package edu.washington.ksf7.quizdroid.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by keegomyneego on 1/29/17.
 */

public class Question {

    public final String question;
    public final Integer indexOfCorrectAnswer;
    public final String[] possibleAnswers;

    private static final String TAG = "Question";

    public Question(String question, String[] possibleAnswers, int indexOfCorrectAnswer) {
        this.question = question;
        this.possibleAnswers = possibleAnswers;
        this.indexOfCorrectAnswer = indexOfCorrectAnswer;
    }

    public Question(JSONObject jsonQuestion, String questionTextKey, String correctAnswerKey, String possibleAnswersKey) {
        // locals to hold data as it gets parsed
        String parsedQuestion = null;
        Integer parsedIndexOfCorrectAnswer = null;
        String[] parsedPossibleAnswers = null;

        try {
            parsedQuestion = jsonQuestion.getString(questionTextKey);
            parsedIndexOfCorrectAnswer = jsonQuestion.getInt(correctAnswerKey);

            JSONArray jsonPossibleAnswers = jsonQuestion.getJSONArray(possibleAnswersKey);
            int answerCount = jsonPossibleAnswers.length();
            parsedPossibleAnswers = new String[answerCount];

            for (int i = 0; i < answerCount; i++) {
                parsedPossibleAnswers[i] = jsonPossibleAnswers.getString(i);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize Question from json data", e);
        }

        // initialize with parsed data
        this.question = parsedQuestion;
        this.indexOfCorrectAnswer = parsedIndexOfCorrectAnswer;
        this.possibleAnswers = parsedPossibleAnswers;
    }
}
