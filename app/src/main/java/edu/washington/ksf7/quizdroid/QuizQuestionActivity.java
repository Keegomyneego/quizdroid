package edu.washington.ksf7.quizdroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import edu.washington.ksf7.quizdroid.Models.MultipleChoiceQuestion;
import edu.washington.ksf7.quizdroid.Models.Quiz;
import edu.washington.ksf7.quizdroid.Models.QuizState;

public class QuizQuestionActivity extends AppCompatActivity {

    public final String TAG = "QuizQuestionActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_question);

        // get data
        MultipleChoiceQuestion currentQuestion = QuizState.currentState.getCurrentQuestion();

        // update views with data
        updateViewsWithQuizInfo(currentQuestion);
    }

    private void updateViewsWithQuizInfo(MultipleChoiceQuestion currentQuestion) {

        // set the question text
        ((TextView) findViewById(R.id.question_text)).setText(currentQuestion.question);

        // add all the possible answers
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.possible_answers);
        for (int i = 0; i < currentQuestion.possibleAnswers.length; i++) {
            final String possibleAnswer = currentQuestion.possibleAnswers[i];
            final int index = i;
            final RadioButton radioButton = new RadioButton(this);

            radioButton.setText(possibleAnswer);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // make submit button visible when an option is selected
                    QuizQuestionActivity.this.findViewById(R.id.submit_button).setVisibility(View.VISIBLE);

                    // record guess
                    QuizState.currentState.guessAnswer(index);
                }
            });
            radioGroup.addView(radioButton);
        }

        // setup submit button
        findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(QuizQuestionActivity.this, QuizAnswerActivity.class);
                startActivity(intent);
            }
        });
    }
}
