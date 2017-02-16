package edu.washington.ksf7.quizdroid.Models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by keegomyneego on 2/15/17.
 */

public class Topic {

    public final String title;
    public final String shortDescription;
    public final String longDescription;
    public final Question[] questions;

    private static final String TAG = "Topic";

    public Topic(String title, String shortDescription, String longDescription, Question[] questions) {
        this.title = title;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.questions = questions;
    }

    public Topic(JSONObject jsonTopic, String titleKey, String descriptionKey, String questionsKey, String questionTextKey, String questionCorrectAnswerKey, String questionPossibleAnswersKey) {
        // locals to hold data as it gets parsed
        String parsedTitle = null;
        String parsedShortDescription = null;
        String parsedLongDescription = null;
        Question[] parsedQuestions = null;

        // attempt to parse data
        try {
            parsedTitle = jsonTopic.getString(titleKey);
            parsedShortDescription = jsonTopic.getString(descriptionKey);
            parsedLongDescription = jsonTopic.getString(descriptionKey);

            JSONArray jsonQuestions = jsonTopic.getJSONArray(questionsKey);
            int questionsCount = jsonQuestions.length();
            parsedQuestions = new Question[questionsCount];

            for (int i = 0; i < questionsCount; i++) {
                JSONObject jsonQuestion = jsonQuestions.getJSONObject(i);
                parsedQuestions[i] = new Question(jsonQuestion, questionTextKey, questionCorrectAnswerKey, questionPossibleAnswersKey);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to initialize Topic from json data", e);
        }

        // initialize with parsed data
        this.title = parsedTitle;
        this.shortDescription = parsedShortDescription;
        this.longDescription = parsedLongDescription;
        this.questions = parsedQuestions;
    }
}
