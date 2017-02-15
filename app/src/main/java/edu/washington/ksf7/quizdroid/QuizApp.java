package edu.washington.ksf7.quizdroid;

import android.app.Application;
import android.util.Log;

/**
 * Created by keegomyneego on 2/14/17.
 */

public class QuizApp extends Application {

    public static final String TAG = "QuizApp";

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "QuizApp created");
    }
}
