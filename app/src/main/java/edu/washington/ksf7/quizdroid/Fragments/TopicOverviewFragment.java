package edu.washington.ksf7.quizdroid.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.washington.ksf7.quizdroid.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class TopicOverviewFragment extends Fragment {

    static final String TAG = "TopicOverviewFragment";

    //----------------------------------------------------------------------------------------------
    // Client Interface
    //----------------------------------------------------------------------------------------------

    public TopicOverviewFragment() {
        // Required empty public constructor
    }

    public void setArguments(String topicTitle, int questionCount) {
        Bundle bundle = new Bundle();
        bundle.putString("topicTitle", topicTitle);
        bundle.putInt("questionCount", questionCount);
        setArguments(bundle);
    }

    public interface Listener {
        void onBeginClicked(View view);
    }

    //----------------------------------------------------------------------------------------------
    // Implementation
    //----------------------------------------------------------------------------------------------

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
        setViewData(mainView, topicTitle, questionCount);

        return mainView;
    }

    private void setViewData(View mainView, String title, int questionCount) {
        // TextViews
        String description = questionCount == 1
                ? "There is " + questionCount + " question in this topic"
                : "There are " + questionCount + " questions in this topic";

        ((TextView) mainView.findViewById(R.id.topic_title)).setText(title);
        ((TextView) mainView.findViewById(R.id.description)).setText(description);

        // Buttons
        mainView.findViewById(R.id.begin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Delegate click logic to parent activity
                try {
                    ((Listener) getActivity()).onBeginClicked(view);
                } catch (ClassCastException e) {
                    Log.w(TAG, "Parent activity does not implement Listener class");
                }
            }
        });
    }

}
