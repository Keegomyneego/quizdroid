package edu.washington.ksf7.quizdroid;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.washington.ksf7.quizdroid.Models.QuizState;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicOverviewFragment extends Fragment {


    public TopicOverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mainView = inflater.inflate(R.layout.fragment_topic_overview, container, false);

        // Extract argument data
        Bundle bundle = getArguments();
        String topicTitle = bundle.getString("topicTitle");
        int questionCount = bundle.getInt("questionCount", -1);

        // Customize views with argument data
        setTopicInfo(mainView, topicTitle, questionCount);
        addEventListeners(mainView);

        return mainView;
    }

    private void setTopicInfo(View mainView, String title, int questionCount) {
        String description = questionCount == 1
                ? "There is " + questionCount + " question in this topic"
                : "There are " + questionCount + " questions in this topic";

        ((TextView) mainView.findViewById(R.id.topic_title)).setText(title);
        ((TextView) mainView.findViewById(R.id.description)).setText(description);
    }

    private void addEventListeners(View mainView) {
        mainView.findViewById(R.id.begin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO transition to next fragment

//                Intent intent = new Intent(TopicOverviewActivity.this, QuizQuestionActivity.class);
//                QuizState.startQuiz(quiz);
//                startActivity(intent);
            }
        });
    }

}
