package edu.washington.ksf7.quizdroid.Controllers;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by keegomyneego on 3/1/17.
 */

public class DownloadManager {

    private static final String TAG = "DownloadManager";

    private static AlarmManager alarmManager;
    private static List<PendingDownload> downloadQueue;

    public interface DownloadListener {
        void onDownloadComplete();
    }

    private class PendingDownload {

        PendingDownload() {}

        public void cancel() {}
    }

    /**
     *
     * @param url
     * @param downloadListener
     * @param updateInterval number of minutes before initiating another download. Does nothing if < 1
     */
    public static void queueDownload(Activity activityContext, String url, DownloadListener downloadListener, int updateInterval) {

        Log.i(TAG, "Queueing download: " + url);

        if (alarmManager == null) {
            alarmManager = (AlarmManager) activityContext.getSystemService(Activity.ALARM_SERVICE);
        }

        // Perform download instantly

        // If valid update interval is supplied, queue download to repeat in the future
        if (updateInterval > 0) {
//            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
//                    SystemClock.elapsedRealtime() + triggerInMillis,
//                    intervalMillis,
//                    alarmIntent);
        }



    }

    public static void clearDownloadQueue() {
        for (PendingDownload pendingDownload : downloadQueue) {
            pendingDownload.cancel();
        }

        downloadQueue.clear();
    }





    public static void startService(Activity activityContext, String phoneNumber, String message, int minuteInterval) {

        Log.i(TAG, "Starting AWTY Service.");

        switch (executionStatus) {
            case STOPPED:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (activityContext.checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                        Log.i(TAG, "Permission to send SMS not yet granted, requesting...");

                        activityContext.requestPermissions(new String[] {
                                Manifest.permission.SEND_SMS
                        }, 1);
                    }
                }

                Intent startAWTYReceiver = new Intent(activityContext, AWTYBroadcastReceiver.class);
                startAWTYReceiver.putExtra("phoneNumber", phoneNumber);
                startAWTYReceiver.putExtra("message", message);

                alarmIntent = PendingIntent.getBroadcast(activityContext, 0, startAWTYReceiver, 0);
                alarmManager = (AlarmManager) activityContext.getSystemService(activityContext.ALARM_SERVICE);

                long triggerInMillis = minutesBeforeFirstFire * 60 * 1000;
                long intervalMillis = minuteInterval * 60 * 1000;

                alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                        SystemClock.elapsedRealtime() + triggerInMillis,
                        intervalMillis,
                        alarmIntent);

                Log.i(TAG, "Scheduled message \"" + message + "\" to be sent to " + phoneNumber + " every " + minuteInterval + " minutes.");

                executionStatus = ExecutionStatus.STARTED;
                break;

            default:
                Log.e(TAG, "AWTYBroadcastReceiver must be stopped before it can be started again!");
        }
    }

    public static void stopService() {

        Log.i(TAG, "Stopping AWTY Service");

        switch (executionStatus) {
            case STARTED:
                if (alarmManager != null) {
                    alarmManager.cancel(alarmIntent);
                }

                executionStatus = ExecutionStatus.STOPPED;
                break;

            default:
                Log.e(TAG, "AWTYBroadcastReceiver must be started before it can be stopped!");
        }
    }
}
