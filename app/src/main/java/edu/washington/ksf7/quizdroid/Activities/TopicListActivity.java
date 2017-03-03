package edu.washington.ksf7.quizdroid.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.washington.ksf7.quizdroid.BroadcastReceivers.DownloadExecutor;
import edu.washington.ksf7.quizdroid.Controllers.DownloadManager;
import edu.washington.ksf7.quizdroid.Controllers.MasterDetailView;
import edu.washington.ksf7.quizdroid.Models.Topic;
import edu.washington.ksf7.quizdroid.QuizApp;
import edu.washington.ksf7.quizdroid.R;
import edu.washington.ksf7.quizdroid.Repositories.TopicRepository;

public class TopicListActivity extends AppCompatActivity implements MasterDetailView.Listener {

    RecyclerView topicListView;
    private static final String TAG = "TopicListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_list);

        TopicRepository.getInstance().startDownloadAndScheduleUpdates(this);
        TopicRepository.getInstance().subscribe(new TopicRepository.Subscriber() {
            @Override
            public void onRepositoryUpdated() {
                Log.i(TAG, "Refreshing adapter");
                updateTopicData();
            }
        });

        initializeToolbar(getString(R.string.activity_topic_list_title));

        // Bind the view to the data
        initializeTopicList();
    }

    @Override
    protected void onResume() {
        Log.i(TAG, "Resuming activity...");
        super.onResume();
        TopicRepository.getInstance().resumeDownloads(this);
    }

    @Override
    protected void onDestroy() {
        TopicRepository.getInstance().stopUpdates();
        super.onDestroy();
    }

    //----------------------------------------------------------------------------------------------
    // Implementation
    //----------------------------------------------------------------------------------------------

    private final List<String> topicTitles = new ArrayList<>();
    private final List<String> topicDescriptions = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    private void initializeToolbar(String title) {
        Toolbar toolbar = ((Toolbar) findViewById(R.id.toolbar));
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
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
        adapter = new MasterDetailView.Adapter(this, topicTitles, topicDescriptions, R.layout.quiz_topic_card, R.id.topic_title, R.id.topic_description);
        topicListView.setAdapter(adapter);

        // Get the topic info to display
        updateTopicData();
    }

    private void updateTopicData() {
        // Get the latest topic info to display
        Topic[] topics = QuizApp.getInstance().getTopicRepository().getAllTopics();

        topicTitles.clear();
        topicDescriptions.clear();

        if (topics != null) {
            for (int i = 0; i < topics.length; i++) {
                topicTitles.add(topics[i].title);
                topicDescriptions.add(topics[i].shortDescription);
            }
        }

        // Notify adapter that data has changed
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    //----------------------------------------------------------------------------------------------
    // Options Menu
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_options_topic_list, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_preferences:
                // Go to preferences
                Intent intent = new Intent(this, PreferencesActivity.class);
                startActivity(intent);

                return true;

            default:
                // User action not recognized, delegate to super class
                return super.onOptionsItemSelected(item);
        }
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
