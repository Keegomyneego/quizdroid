package edu.washington.ksf7.quizdroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import edu.washington.ksf7.quizdroid.Fragments.QuizAnswerFragment;
import edu.washington.ksf7.quizdroid.Fragments.QuizQuestionFragment;
import edu.washington.ksf7.quizdroid.Models.Topic;
import edu.washington.ksf7.quizdroid.QuizApp;
import edu.washington.ksf7.quizdroid.R;
import edu.washington.ksf7.quizdroid.Fragments.TopicOverviewFragment;

public class QuizActivity extends AppCompatActivity implements TopicOverviewFragment.Listener, QuizQuestionFragment.Listener, QuizAnswerFragment.Listener {

    final static String TAG = "QuizActivity";

    //----------------------------------------------------------------------------------------------
    // Client Interface
    //----------------------------------------------------------------------------------------------

    public static void putArguments(Intent intent, int quizNumber) {
        intent.putExtra("topicNumber", quizNumber);
    }

    //----------------------------------------------------------------------------------------------
    // Implementation
    //----------------------------------------------------------------------------------------------

    // Quiz Config
    private Topic topic;
    private int topicNumber;
    private int questionCount;

    // Quiz State
    private int currentQuestion;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        initializeToolbar(getString(R.string.activity_quiz_title));

        // Get arguments from intent
        Intent intent = getIntent();
        topicNumber = intent.getIntExtra("topicNumber", -1);
        if (topicNumber == -1) {
            Log.e(TAG, "Arguments not properly put into intent");
        }

        // Initialize topic data
        topic = QuizApp.getInstance().getTopicRepository().getTopic(topicNumber);
        questionCount = topic.questions.length;

        // Get data for initial fragment
        String topicTitle = topic.title;
        String topicDescription = topic.shortDescription;

        // Load in the topic overview fragment
        TopicOverviewFragment topicOverviewFragment = new TopicOverviewFragment();
        topicOverviewFragment.setArguments(topicTitle, topicDescription, questionCount);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.quiz_fragment_frame, topicOverviewFragment)
                .commit();
    }

    private void initializeToolbar(String title) {
        ((TextView) findViewById(R.id.toolbar_title)).setText(title);
    }

    //----------------------------------------------------------------------------------------------
    // Fragment Interface Implementations
    //----------------------------------------------------------------------------------------------

    public void onBeginClicked(View view) {
        // Start from the beginning
        resetQuizState();

        // Transition to taking the first question in the topic
        QuizQuestionFragment quizQuestionFragment = new QuizQuestionFragment();
        quizQuestionFragment.setArguments(topicNumber, currentQuestion);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.quiz_fragment_frame, quizQuestionFragment)
                .commit();
    }

    public void onSubmitAnswerClicked(View view, int guess) {
        // Calculate results
        String selectedAnswer = topic.getPossibleAnswer(currentQuestion, guess);
        String correctAnswer = topic.getCorrectAnswer(currentQuestion);
        score += topic.guessIsCorrect(currentQuestion, guess) ? 1 : 0;
        int questionsAnswered = currentQuestion + 1;
        boolean isLastQuestion = questionsAnswered >= questionCount;
        
        // Transition to the answer page
        QuizAnswerFragment quizAnswerFragment = new QuizAnswerFragment();
        quizAnswerFragment.setArguments(selectedAnswer, correctAnswer, score, questionsAnswered, isLastQuestion);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.quiz_fragment_frame, quizAnswerFragment)
                .commit();
    }

    public void onDoneClicked(View view, boolean isLastQuestion) {
        // Move to next question
        currentQuestion++;

        if (isLastQuestion) {
            // Transition back to the list of topics
            Intent intent = new Intent(this, TopicListActivity.class);
            startActivity(intent);
        } else {
            // Transition to the next question
            QuizQuestionFragment quizQuestionFragment = new QuizQuestionFragment();
            quizQuestionFragment.setArguments(topicNumber, currentQuestion);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.quiz_fragment_frame, quizQuestionFragment)
                    .commit();
        }
    }

    private void resetQuizState() {
        currentQuestion = 0;
        score = 0;
    }
}
