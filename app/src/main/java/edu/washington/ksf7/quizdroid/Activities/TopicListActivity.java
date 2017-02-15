package edu.washington.ksf7.quizdroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import edu.washington.ksf7.quizdroid.Controllers.MasterDetailView;
import edu.washington.ksf7.quizdroid.Data;
import edu.washington.ksf7.quizdroid.R;

public class TopicListActivity extends AppCompatActivity implements MasterDetailView.Listener {

    RecyclerView topicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        // Load all the quiz data
        Data.loadQuizzes();

        // Bind the view to the data
        initializeTopicList();
    }

    private void initializeTopicList() {
        // Get the recycler
        topicListView = (RecyclerView) findViewById(R.id.topic_list);

        // Set some basic properties
        topicListView.setHasFixedSize(true);

        // Use a simple linear layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        topicListView.setLayoutManager(layoutManager);

        // Create the adapter
        RecyclerView.Adapter adapter = new MasterDetailView.Adapter(this, Data.getTopics(), R.layout.quiz_topic_card, R.id.topic);
        topicListView.setAdapter(adapter);
    }

    //----------------------------------------------------------------------------------------------
    // MasterDetailView.Listener Implementation
    //----------------------------------------------------------------------------------------------

    public void onDetailViewClicked(View view, int position) {
        // Launch the quiz activity
        Intent intent = new Intent(this, QuizActivity.class);
        QuizActivity.putArguments(intent, position);
        startActivity(intent);
    }
}
