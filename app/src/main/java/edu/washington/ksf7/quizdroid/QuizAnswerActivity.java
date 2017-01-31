package edu.washington.ksf7.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.washington.ksf7.quizdroid.Models.MultipleChoiceQuestion;
import edu.washington.ksf7.quizdroid.Models.Quiz;
import edu.washington.ksf7.quizdroid.Models.QuizState;

public class QuizAnswerActivity extends AppCompatActivity {

    public final String TAG = "QuizAnswerActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answer);

        // get data
        String selectedAnswer = QuizState.currentState.getCurrentGuess();
        String correctAnswer = QuizState.currentState.getCorrectAnswer();
        int score = QuizState.currentState.getCurrentScore();
        int guessesSoFar = QuizState.currentState.getGuessesMadeSoFar();
        boolean isLastQuestion = QuizState.currentState.isLastQuestion();

        // Update views with data
        updateViewsWithQuizInfo(selectedAnswer, correctAnswer, score, guessesSoFar, isLastQuestion);
    }

    private void updateViewsWithQuizInfo(String selectedAnswer, String correctAnswer, int score, int guessesSoFar, final boolean isLastQuestion) {
        final String scoreText = "You have " + score + " out of " + guessesSoFar + " correct";

        // show the selected answer
        ((TextView) findViewById(R.id.selected_answer)).setText(selectedAnswer);

        // show the correct answer
        ((TextView) findViewById(R.id.correct_answer)).setText(correctAnswer);

        // add all the possible answers
        ((TextView) findViewById(R.id.score)).setText(scoreText);

        // setup submit button
        Button submitButton = (Button) findViewById(R.id.submit_button);
        submitButton.setText(isLastQuestion
            ? "Finish"
            : "Next"
        );
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QuizState.currentState.moveToNextQuestion();
                Intent intent = new Intent(QuizAnswerActivity.this, isLastQuestion
                        ? MainActivity.class
                        : QuizQuestionActivity.class
                );
                startActivity(intent);
            }
        });
    }

}
