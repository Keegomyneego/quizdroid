package edu.washington.ksf7.quizdroid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.washington.ksf7.quizdroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizAnswerFragment extends Fragment {

    //----------------------------------------------------------------------------------------------
    // Client Interface
    //----------------------------------------------------------------------------------------------

    public QuizAnswerFragment() {
        // Required empty public constructor
    }

    public void setArguments(int quizNumber, int questionCount) {
        Bundle bundle = new Bundle();
        bundle.putInt("quizNumber", quizNumber);
        setArguments(bundle);
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
        String topicTitle = bundle.getString("topicTitle");
        int quizNumber = bundle.getInt("quizNumber", -1);
        int questionCount = bundle.getInt("questionCount", -1);

        // Customize views with argument data
        setViewData(mainView, topicTitle, quizNumber, questionCount);

        return mainView;
    }

    private void setViewData(View mainView, String title, final int quizNumber, int questionCount) {

    }
}
