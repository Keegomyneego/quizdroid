package edu.washington.ksf7.quizdroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.washington.ksf7.quizdroid.Controllers.MasterDetailView;
import edu.washington.ksf7.quizdroid.Data;
import edu.washington.ksf7.quizdroid.Fragments.QuizQuestionFragment;
import edu.washington.ksf7.quizdroid.Models.Quiz;
import edu.washington.ksf7.quizdroid.R;
import edu.washington.ksf7.quizdroid.Fragments.TopicOverviewFragment;

public class QuizActivity extends AppCompatActivity implements TopicOverviewFragment.Listener, QuizQuestionFragment.Listener {

    final static String TAG = "QuizActivity";

    //----------------------------------------------------------------------------------------------
    // Client Interface
    //----------------------------------------------------------------------------------------------

    public static void putArguments(Intent intent, int quizNumber) {
        intent.putExtra("quizNumber", quizNumber);
    }

    //----------------------------------------------------------------------------------------------
    // Implementation
    //----------------------------------------------------------------------------------------------

    private Quiz quiz;
    private int quizNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // Get arguments from intent
        Intent intent = getIntent();
        quizNumber = intent.getIntExtra("quizNumber", -1);
        if (quizNumber == -1) {
            Log.e(TAG, "Arguments not properly put into intent");
        }

        // Get data for my fragments
        quiz = Data.getQuiz(quizNumber);
        String topicTitle = quiz.getTopic();
        int questionCount = quiz.getQuestions().size();

        // Load in the topic overview fragment
        TopicOverviewFragment topicOverviewFragment = new TopicOverviewFragment();
        topicOverviewFragment.setArguments(topicTitle, questionCount);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.quiz_fragment_frame, topicOverviewFragment)
                .commit();
    }

    //----------------------------------------------------------------------------------------------
    // Fragment Interface Implementations
    //----------------------------------------------------------------------------------------------

    public void onBeginButtonClicked(View view) {
        // Setup and transition to taking the first question in the quiz
        QuizQuestionFragment quizQuestionFragment = new QuizQuestionFragment();
        quizQuestionFragment.setArguments(quizNumber, 0);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.quiz_fragment_frame, quizQuestionFragment)
                .commit();
    }

    public void onSubmitAnswerClicked(View view, int answerNumber) {
        Log.d(TAG, "TODO submit answer #" + answerNumber);
    }
}
