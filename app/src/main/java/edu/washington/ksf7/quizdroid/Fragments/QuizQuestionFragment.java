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
public class QuizQuestionFragment extends Fragment {


    public QuizQuestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz_question, container, false);
    }

}
