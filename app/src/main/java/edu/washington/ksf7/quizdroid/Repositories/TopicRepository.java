package edu.washington.ksf7.quizdroid.Repositories;

import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.washington.ksf7.quizdroid.Data;
import edu.washington.ksf7.quizdroid.Models.Question;
import edu.washington.ksf7.quizdroid.Models.Quiz;
import edu.washington.ksf7.quizdroid.Models.Topic;

/**
 * Created by keegomyneego on 2/15/17.
 */

public class TopicRepository {

    private static final String TAG = "TopicRepository";
    private static TopicRepository instance;

    // Config - default JSON keys
    private static final String topicTitleKey = "title";
    private static final String topicDescriptionKey = "desc";
    private static final String topicQuestionsKey = "questions";
    private static final String questionTextKey = "text";
    private static final String questionCorrectAnswerKey = "answer";
    private static final String questionPossibleAnswersKey = "answers";

    // Data Fields
    private final Topic[] topics;

    //
    // Public Interface
    //

    public static TopicRepository getInstance() {
        // Return instance if it exists, otherwise initialize a new one
        if (instance != null) {
            return instance;
        }

        // Fetch remote data
        String topicData = fetchTopicData();

        // Parse it
        Topic[] parsedTopics = parseTopics(topicData);

        // Initialize and return new instance
        instance = new TopicRepository(parsedTopics);

        return instance;
    }

    public Topic[] getTopics() {
        return topics;
    }

    //
    // Contructors
    //

    private TopicRepository(Topic[] topics) {
        this.topics = topics;
    }

    //
    // Private Helpers
    //

    private static String fetchTopicData() {
        return Data.questionsJSON;
    }

    @Nullable
    private static Topic[] parseTopics(String unparsedJSONArray) {
        Topic[] parsedTopics = null;

        // Attempt to parse data
        try {
            JSONArray jsonTopics = new JSONArray(unparsedJSONArray);
            int topicCount = jsonTopics.length();
            parsedTopics = new Topic[topicCount];

            for (int i = 0; i < topicCount; i++) {
                JSONObject jsonTopic = jsonTopics.getJSONObject(i);
                parsedTopics[i] = new Topic(
                        jsonTopic,
                        topicTitleKey,
                        topicDescriptionKey,
                        topicQuestionsKey,
                        questionTextKey,
                        questionCorrectAnswerKey,
                        questionPossibleAnswersKey);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to parse topic data", e);
        }

        return parsedTopics;
    }
}
