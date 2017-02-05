package edu.washington.ksf7.quizdroid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import edu.washington.ksf7.quizdroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizAnswerFragment extends Fragment {

    static final String TAG = "QuizAnswerFragment";

    //----------------------------------------------------------------------------------------------
    // Client Interface
    //----------------------------------------------------------------------------------------------

    public QuizAnswerFragment() {
        // Required empty public constructor
    }

    public void setArguments(String selectedAnswer, String correctAnswer, int score, int questionsAnswered, final boolean isLastQuestion) {
        Bundle bundle = new Bundle();
        bundle.putString("selectedAnswer", selectedAnswer);
        bundle.putString("correctAnswer", correctAnswer);
        bundle.putInt("score", score);
        bundle.putInt("questionsAnswered", questionsAnswered);
        bundle.putBoolean("isLastQuestion", isLastQuestion);
        setArguments(bundle);
    }

    public interface Listener {
        void onDoneClicked(View view, boolean isLastQuestion);
    }

    //----------------------------------------------------------------------------------------------
    // Implementation
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_quiz_answer, container, false);

        // Extract argument data
        Bundle bundle = getArguments();
        String selectedAnswer = bundle.getString("selectedAnswer");
        String correctAnswer = bundle.getString("correctAnswer");
        int score = bundle.getInt("score");
        int questionsAnswered = bundle.getInt("questionsAnswered");
        boolean isLastQuestion = bundle.getBoolean("isLastQuestion");

        // Customize views with argument data
        setViewData(mainView, selectedAnswer, correctAnswer, score, questionsAnswered, isLastQuestion);

        return mainView;
    }

    private void setViewData(View mainView, String selectedAnswer, String correctAnswer, int score, int questionsAnswered, final boolean isLastQuestion) {
        final String scoreText = "You have " + score + " out of " + questionsAnswered + " correct";

        // Show the selected answer
        ((TextView) mainView.findViewById(R.id.selected_answer)).setText(selectedAnswer);

        // Show the correct answer
        ((TextView) mainView.findViewById(R.id.correct_answer)).setText(correctAnswer);

        // Add all the possible answers
        ((TextView) mainView.findViewById(R.id.score)).setText(scoreText);

        // Setup submit button
        Button doneButton = (Button) mainView.findViewById(R.id.done_button);
        doneButton.setText(isLastQuestion
                ? "Finish"
                : "Next"
        );
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delegate click logic to parent activity
                try {
                    ((Listener) getActivity()).onDoneClicked(view, isLastQuestion);
                } catch (ClassCastException e) {
                    Log.w(TAG, "Parent activity does not implement Listener class");
                }
            }
        });
    }
}
