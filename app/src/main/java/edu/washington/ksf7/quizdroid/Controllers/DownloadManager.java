package edu.washington.ksf7.quizdroid.Controllers;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import edu.washington.ksf7.quizdroid.BroadcastReceivers.DownloadExecutor;

/**
 * Created by keegomyneego on 3/1/17.
 */

public class DownloadManager {

    private static final String TAG = "DownloadManager";

    private static AlarmManager alarmManager;
    private static PendingIntent downloadQuestionsData;
    private static boolean questionsDataDownloadIsQueued = false;

    /**
     * Downloads immediately and queues repeated downloads in the future.
     * @param updateInterval number of minutes before initiating another download. Does nothing if < 1
     */
    public static void downloadAndQueueUpdates(Context context, String url, DownloadExecutor.DownloadHandler downloadHandler, int updateInterval) {
        download(context, url, downloadHandler);
        queueUpdates(context, url, downloadHandler, updateInterval);
    }

    /**
     * Downloads immediately
     */
    public static void download(Context context, String url, DownloadExecutor.DownloadHandler downloadHandler) {
        DownloadExecutor.downloadImmediately(context, url, downloadHandler);
    }

    /**
     * Queues a download to repeat at a specified interval.
     * @param updateInterval number of minutes before initiating another download. Does nothing if < 1
     */
    public static void queueUpdates(Context context, String url, DownloadExecutor.DownloadHandler downloadHandler, int updateInterval) {
        // Ensure alarm manager exists
        if (alarmManager == null) {
            alarmManager = (AlarmManager) context.getSystemService(Activity.ALARM_SERVICE);
        }

        // If valid update interval is supplied, queue download to repeat in the future
        if (updateInterval > 0) {
            Log.i(TAG, "Queueing download of " + url + " to run every " + updateInterval + " minutes");

            // Store intent so it can be cancelled later
            downloadQuestionsData = DownloadExecutor.getDownloadIntent(context, url, downloadHandler);
            long intervalMillis = updateInterval * 60 * 1000;

            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                    SystemClock.elapsedRealtime() + intervalMillis,
                    intervalMillis,
                    downloadQuestionsData);

            questionsDataDownloadIsQueued = true;
        }
    }

    public static void changeUpdateInterval(Context context, String url, DownloadExecutor.DownloadHandler downloadHandler, int newUpdateInterval) {
        clearDownloadQueue();
        queueUpdates(context, url, downloadHandler, newUpdateInterval);
    }

    public static void clearDownloadQueue() {

        Log.i(TAG, "cancelling all queued downloads");

        alarmManager.cancel(downloadQuestionsData);
        questionsDataDownloadIsQueued = false;
    }

    public static boolean questionsDataDownloadIsQueued() {
        return questionsDataDownloadIsQueued;
    }
}
