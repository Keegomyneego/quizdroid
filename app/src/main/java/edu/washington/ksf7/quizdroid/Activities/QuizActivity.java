package edu.washington.ksf7.quizdroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import edu.washington.ksf7.quizdroid.Controllers.MasterDetailView;
import edu.washington.ksf7.quizdroid.Data;
import edu.washington.ksf7.quizdroid.Fragments.QuizAnswerFragment;
import edu.washington.ksf7.quizdroid.Fragments.QuizQuestionFragment;
import edu.washington.ksf7.quizdroid.Models.Quiz;
import edu.washington.ksf7.quizdroid.R;
import edu.washington.ksf7.quizdroid.Fragments.TopicOverviewFragment;

public class QuizActivity extends AppCompatActivity implements TopicOverviewFragment.Listener, QuizQuestionFragment.Listener, QuizAnswerFragment.Listener {

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
    private int currentQuestion;
    private int score;
    private int questionCount;

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

        // Initialize quiz data
        quiz = Data.getQuiz(quizNumber);
        questionCount = quiz.getQuestions().size();

        // Get data for initial fragment
        String topicTitle = quiz.getTopic();

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

    public void onBeginClicked(View view) {
        // Start from the beginning
        currentQuestion = 0;

        // Transition to taking the first question in the quiz
        QuizQuestionFragment quizQuestionFragment = new QuizQuestionFragment();
        quizQuestionFragment.setArguments(quizNumber, currentQuestion);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.quiz_fragment_frame, quizQuestionFragment)
                .commit();
    }

    public void onSubmitAnswerClicked(View view, int guess) {
        // Calculate results
        String selectedAnswer = quiz.getPossibleAnswer(currentQuestion, guess);
        String correctAnswer = quiz.getCorrectAnswer(currentQuestion);
        score += quiz.guessIsCorrect(currentQuestion, guess) ? 1 : 0;
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
            quizQuestionFragment.setArguments(quizNumber, currentQuestion);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.quiz_fragment_frame, quizQuestionFragment)
                    .commit();
        }
    }
}
