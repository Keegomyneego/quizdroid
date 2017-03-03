package edu.washington.ksf7.quizdroid.Repositories;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import edu.washington.ksf7.quizdroid.BroadcastReceivers.DownloadExecutor;
import edu.washington.ksf7.quizdroid.Controllers.DownloadManager;
import edu.washington.ksf7.quizdroid.Data;
import edu.washington.ksf7.quizdroid.Models.Topic;

/**
 * Created by keegomyneego on 2/15/17.
 */

public class TopicRepository {

    private static final String TAG = "TopicRepository";
    private static TopicRepository instance;

    // Config - data fetching settings
    public static final String QUESTIONS_FILE_NAME = "questions.json";
    private static String questionDataURL = "http://tednewardsandbox.site44.com/questions.json";
    private static int questionDataRefreshRate = 5;

    // Config - default JSON keys
    private static final String topicTitleKey = "title";
    private static final String topicDescriptionKey = "desc";
    private static final String topicQuestionsKey = "questions";
    private static final String questionTextKey = "text";
    private static final String questionCorrectAnswerKey = "answer";
    private static final String questionPossibleAnswersKey = "answers";

    // State
    private static boolean downloadPaused = false;
    private static boolean updatesPaused = false;
    private static boolean updatesScheduled = false;

    // Data Fields
    private final Topic[] topics;

    // Subscribers
    private static List<Subscriber> subscribers = new ArrayList<>();

    //
    // Public Static Interface
    //

    public interface Subscriber {
        void onRepositoryUpdated();
    }

    public static TopicRepository getInstance() {
        // Return instance if it exists, otherwise initialize a new one
        if (instance != null) {
            return instance;
        }

        // Fetch remote data
        String topicData = fetchTopicData();

        // Parse it
        Topic[] parsedTopics = parseTopics(topicData);

        // Initialize and return new instance
        instance = new TopicRepository(parsedTopics);

        return instance;
    }

    private static DownloadExecutor.DownloadHandler topicsDownloadHandler = new DownloadExecutor.DownloadHandler() {
        @Override
        public void onDownloadComplete(Context context) {
            Toast.makeText(context, "New topics downloaded", Toast.LENGTH_SHORT).show();
            Log.i(TAG, "New topics downloaded");

            // Notify subscribers that data has been updated
            for (Subscriber subscriber : subscribers) {
                subscriber.onRepositoryUpdated();
            }
        }
    };

    //
    // Public Instance Interface
    //

    public static String getQuestionDataURL() {
        return questionDataURL;
    }
    public static int getQuestionDataRefreshRate() {
        return questionDataRefreshRate;
    }

    // Data Management - downloads and updates

    public void startDownloadAndScheduleUpdates(Activity activityContext) {
        Log.d(TAG, "v -- startDownloadAndScheduleUpdates -- v");
        // Make sure we have necessary permissions
        ensurePermissions(activityContext);

        scheduleUpdates(activityContext);
        startDownload(activityContext);
        Log.d(TAG, "^ -- startDownloadAndScheduleUpdates -- ^");
    }

    public void pauseDownloads() {
        Log.i(TAG, "Pausing all downloads");

        // pause immediate download
        downloadPaused = true;

        // pause updates if they've been scheduled
        if (updatesScheduled) {
            updatesPaused = true;
            stopUpdates();
        }
    }

    public void resumeDownloads(Activity activityContext) {
        if (DownloadManager.airplaneModeIsOn(activityContext)) {
            // Skip resuming if airplane mode is still on
            return;
        }

        Log.i(TAG, "Resuming all downloads");

        if (updatesPaused) {
            // reschedule updates
            scheduleUpdates(activityContext);
        }

        if (downloadPaused) {
            // resume immediate download
            startDownload(activityContext);
        }
    }

    public void stopUpdates() {
        Log.i(TAG, "Unscheduling updates");

        DownloadManager.clearDownloadQueue();
        updatesScheduled = false;
    }

    // Other stuff

    public void setQuestionDataSettings(Context context, String url, int refreshRate) {
        questionDataURL = url;
        questionDataRefreshRate = refreshRate;

        DownloadManager.changeUpdateInterval(context, url, topicsDownloadHandler, refreshRate);
    }

    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);
    }

    // Data Retreival

    @Nullable
    public Topic[] getAllTopics() {
        return topics;
    }

    @Nullable
    public Topic getTopic(int index) {
        if (index >= topics.length) {
            return null;
        }

        return topics[index];
    }

    //
    // Contructors
    //

    private TopicRepository(Topic[] topics) {
        this.topics = topics;
    }

    //
    // Private Helpers
    //

    private static String fetchTopicData() {
        return Data.questionsJSON;
    }

    @Nullable
    private static Topic[] parseTopics(String unparsedJSONArray) {
        Topic[] parsedTopics = null;

        // Attempt to parse data
        try {
            JSONArray jsonTopics = new JSONArray(unparsedJSONArray);
            int topicCount = jsonTopics.length();
            parsedTopics = new Topic[topicCount];

            for (int i = 0; i < topicCount; i++) {
                JSONObject jsonTopic = jsonTopics.getJSONObject(i);
                parsedTopics[i] = new Topic(
                        jsonTopic,
                        topicTitleKey,
                        topicDescriptionKey,
                        topicQuestionsKey,
                        questionTextKey,
                        questionCorrectAnswerKey,
                        questionPossibleAnswersKey);
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to parse topic data", e);
        }

        return parsedTopics;
    }

    private void ensurePermissions(Activity activityContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activityContext.checkSelfPermission(Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission to use the internet not yet granted, requesting...");

                activityContext.requestPermissions(new String[] {
                        Manifest.permission.INTERNET
                }, 1);
            }

            if (activityContext.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission to use local storage not yet granted, requesting...");

                activityContext.requestPermissions(new String[] {
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
            }

            if (activityContext.checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission to check internet connectivity not yet granted, requesting...");

                activityContext.requestPermissions(new String[] {
                        Manifest.permission.ACCESS_NETWORK_STATE
                }, 1);
            }
        } else {
            Log.d(TAG, "Unable to manually request permission for internet and local storage use");
        }
    }

    private void startDownload(Activity activityContext) {

        Log.i(TAG, "Starting immediate download");
        downloadPaused = false;

        DownloadManager.download(activityContext,
                TopicRepository.questionDataURL,
                TopicRepository.topicsDownloadHandler);
    }

    private void scheduleUpdates(Activity activityContext) {

        if (!updatesScheduled) {
            Log.i(TAG, "Scheduling updates");

            updatesPaused = false;
            updatesScheduled = true;

            DownloadManager.queueUpdates(activityContext,
                    TopicRepository.questionDataURL,
                    TopicRepository.topicsDownloadHandler,
                    TopicRepository.questionDataRefreshRate);
        }
    }
}
