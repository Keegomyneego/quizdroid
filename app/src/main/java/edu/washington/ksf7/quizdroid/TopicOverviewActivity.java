package edu.washington.ksf7.quizdroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.washington.ksf7.quizdroid.Models.Quiz;

public class TopicOverviewActivity extends AppCompatActivity {

    private final String TAG = "TopicOverviewActivity";
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_overview);

        // Get data

        int position = getIntent().getIntExtra("position", -1);
        if (position == -1) {
            Log.e(TAG, "No position found in intent!");
        }

        quiz = Data.quizzes.get(position);

        // Put data into view
        ((TextView) findViewById(R.id.topic_title)).setText(quiz.getTopic());
    }

}
