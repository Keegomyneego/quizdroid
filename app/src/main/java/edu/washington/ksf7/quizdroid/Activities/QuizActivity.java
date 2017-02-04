package edu.washington.ksf7.quizdroid.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import edu.washington.ksf7.quizdroid.Controllers.MasterDetailView;
import edu.washington.ksf7.quizdroid.Data;
import edu.washington.ksf7.quizdroid.Models.Quiz;
import edu.washington.ksf7.quizdroid.R;
import edu.washington.ksf7.quizdroid.Fragments.TopicOverviewFragment;

public class QuizActivity extends AppCompatActivity {

    private final String TAG = "QuizActivity";
    private Quiz quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        // This is a detail in a Master-Detail setup,
        // get this detail activity's index in the master list
        int position = MasterDetailView.Controller.getDetailPosition(getIntent());

        // Get data
        quiz = Data.getQuiz(position);

        // Put data into bundle for fragment
        Bundle bundle = new Bundle();
        bundle.putString("topicTitle", quiz.getTopic());
        bundle.putInt("questionCount", quiz.getQuestions().size());

        TopicOverviewFragment topicOverviewFragment = new TopicOverviewFragment();
        topicOverviewFragment.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.quiz_fragment_frame, topicOverviewFragment)
                .commit();
    }
}
