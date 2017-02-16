package edu.washington.ksf7.quizdroid.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import edu.washington.ksf7.quizdroid.Controllers.MasterDetailView;
import edu.washington.ksf7.quizdroid.Models.Topic;
import edu.washington.ksf7.quizdroid.QuizApp;
import edu.washington.ksf7.quizdroid.R;

public class TopicListActivity extends AppCompatActivity implements MasterDetailView.Listener {

    RecyclerView topicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

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

        // Get the topic info to display
        Topic[] topics = QuizApp.getInstance().getTopicRepository().getAllTopics();
        String[] topicTitles;
        String[] topicDescriptions;

        if (topics == null) {
            topicTitles = new String[0];
            topicDescriptions = new String[0];
        } else {
            topicTitles = new String[topics.length];
            topicDescriptions = new String[topics.length];

            for (int i = 0; i < topics.length; i++) {
                topicTitles[i] = topics[i].title;
                topicDescriptions[i] = topics[i].shortDescription;
            }
        }

        // Create the adapter
        RecyclerView.Adapter adapter = new MasterDetailView.Adapter(this, topicTitles, topicDescriptions, R.layout.quiz_topic_card, R.id.topic_title, R.id.topic_description);
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
