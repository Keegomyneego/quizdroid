package edu.washington.ksf7.quizdroid.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import edu.washington.ksf7.quizdroid.Data;
import edu.washington.ksf7.quizdroid.Models.MultipleChoiceQuestion;
import edu.washington.ksf7.quizdroid.Models.Quiz;
import edu.washington.ksf7.quizdroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class QuizQuestionFragment extends Fragment {

    static final String TAG = "QuizQuestionFragment";

    //----------------------------------------------------------------------------------------------
    // Client Interface
    //----------------------------------------------------------------------------------------------

    public QuizQuestionFragment() {
        // Required empty public constructor
    }

    public void setArguments(int quizNumber, int questionNumber) {
        Bundle bundle = new Bundle();
        bundle.putInt("quizNumber", quizNumber);
        bundle.putInt("questionNumber", questionNumber);
        setArguments(bundle);
    }

    public interface Listener {
        void onSubmitAnswerClicked(View view, int answerNumber);
    }

    //----------------------------------------------------------------------------------------------
    // Implementation
    //----------------------------------------------------------------------------------------------

    private int currentGuess = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_quiz_question, container, false);

        // Extract argument data
        Bundle bundle = getArguments();
        int quizNumber = bundle.getInt("quizNumber", -1);
        int questionNumber = bundle.getInt("questionNumber", -1);

        Quiz currentQuiz = Data.getQuiz(quizNumber);
        MultipleChoiceQuestion currentQuestion = currentQuiz.getQuestions().get(questionNumber);

        // Customize views with argument data
        setViewData(mainView, currentQuestion);

        return mainView;
    }

    private void setViewData(final View mainView, final MultipleChoiceQuestion currentQuestion) {

        // Set the question text
        ((TextView) mainView.findViewById(R.id.question_text)).setText(currentQuestion.question);

        // Add all the possible answers
        final RadioGroup radioGroup = (RadioGroup) mainView.findViewById(R.id.possible_answers);
        for (int i = 0; i < currentQuestion.possibleAnswers.length; i++) {
            final String possibleAnswer = currentQuestion.possibleAnswers[i];
            final int index = i;
            final RadioButton radioButton = new RadioButton(getContext());

            radioButton.setText(possibleAnswer);
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    // make submit button visible when an option is selected
                    mainView.findViewById(R.id.submit_button).setVisibility(View.VISIBLE);

                    // record guess
                    currentGuess = index;
                }
            });
            radioGroup.addView(radioButton);
        }

        // Setup submit button
        mainView.findViewById(R.id.submit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delegate click logic to parent activity
                try {
                    ((Listener) getActivity()).onSubmitAnswerClicked(view, currentGuess);
                } catch (ClassCastException e) {
                    Log.w(TAG, "Parent activity does not implement Listener class");
                }
            }
        });
    }
}
