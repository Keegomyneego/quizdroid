package edu.washington.ksf7.quizdroid;

import android.app.Application;
import android.util.Log;

import edu.washington.ksf7.quizdroid.Repositories.TopicRepository;

/**
 * Created by keegomyneego on 2/14/17.
 */

public class QuizApp extends Application {

    private static final String TAG = "QuizApp";

    private static QuizApp instance;

    private TopicRepository topicRepository;

    public static QuizApp getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "QuizApp created");

        QuizApp.instance = this;
        this.topicRepository = TopicRepository.getInstance();
    }

    public TopicRepository getTopicRepository() {
        return topicRepository;
    }
}
