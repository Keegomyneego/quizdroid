package edu.washington.ksf7.quizdroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.washington.ksf7.quizdroid.Controllers.MasterDetailController;
import edu.washington.ksf7.quizdroid.Models.Quiz;
import edu.washington.ksf7.quizdroid.Models.QuizState;

public class TopicOverviewActivity extends AppCompatActivity {

    private final String TAG = "TopicOverviewActivity";
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_overview);

        // This is a detail in a Master-Detail setup,
        // get this detail activity's index in the master list
        int position = MasterDetailController.getDetailPosition(getIntent());

        // Get data
        quiz = Data.quizzes.get(position);

        // Put data into view
        String topicTitle = quiz.getTopic();
        int questionCount = quiz.getQuestions().size();
        setTopicInfo(topicTitle, questionCount);

        // Add event listeners
        addEventListeners(position);
    }

    private void setTopicInfo(String title, int questionCount) {
        String description = questionCount == 1
                ? "There is " + questionCount + " question in this topic"
                : "There are " + questionCount + " questions in this topic";

        ((TextView) findViewById(R.id.topic_title)).setText(title);
        ((TextView) findViewById(R.id.description)).setText(description);
    }

    private void addEventListeners(final int quizId) {
        findViewById(R.id.begin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TopicOverviewActivity.this, QuizQuestionActivity.class);
                QuizState.startQuiz(quiz);
                startActivity(intent);
            }
        });
    }

}
