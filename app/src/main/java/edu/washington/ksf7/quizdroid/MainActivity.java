package edu.washington.ksf7.quizdroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.washington.ksf7.quizdroid.Adapters.SimpleStringListAdapter;
import edu.washington.ksf7.quizdroid.Models.MultipleChoiceQuestion;
import edu.washington.ksf7.quizdroid.Models.Quiz;

public class MainActivity extends AppCompatActivity {

    RecyclerView topicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load all the quiz data
        Data.loadQuizzes();

        // Bind the view to the data
        initializeTopicList();
    }

    protected void initializeTopicList() {
        // Get the recycler
        topicListView = (RecyclerView) findViewById(R.id.topic_list);

        // Set some basic properties
        topicListView.setHasFixedSize(true);

        // Use a simple linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        topicListView.setLayoutManager(layoutManager);

        // Create the adapter
        RecyclerView.Adapter adapter = new SimpleStringListAdapter(Data.quizTopics, R.layout.quiz_topic_card, R.id.topic);
        topicListView.setAdapter(adapter);
    }


}
