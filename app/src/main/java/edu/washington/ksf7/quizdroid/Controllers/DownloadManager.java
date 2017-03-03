package edu.washington.ksf7.quizdroid.Controllers;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import edu.washington.ksf7.quizdroid.BroadcastReceivers.DownloadExecutor;

/**
 * Created by keegomyneego on 3/1/17.
 */

public class DownloadManager {

    private static final String TAG = "DownloadManager";

    private static AlarmManager alarmManager;
    private static PendingIntent downloadQuestionsData;

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
        }
    }

    public static void changeUpdateInterval(Context context, String url, DownloadExecutor.DownloadHandler downloadHandler, int newUpdateInterval) {
        clearDownloadQueue();
        queueUpdates(context, url, downloadHandler, newUpdateInterval);
    }

    public static void clearDownloadQueue() {

        Log.i(TAG, "cancelling all queued downloads");

        alarmManager.cancel(downloadQuestionsData);
    }

    // Connectivity Methods

    public static void promptUserToDisableAirplaneMode(final Context context) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Airplane Mode Detected");
        alertDialog.setMessage("An internet connection is required to download topic data, would you like to disable airplane mode?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes please", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Settings.ACTION_AIRPLANE_MODE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static void promptUserToRetryDownload(final Context context, String contentDescription, final Runnable positiveButtonClickAction) {
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("No Internet Connection");
        alertDialog.setMessage("An attempt to download " + contentDescription + ", would you like to try again?");
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No thanks", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes please", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                positiveButtonClickAction.run();
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public static boolean hasInternetConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean airplaneModeIsOn(Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON) != 0;
            } else {
                Log.e(TAG, "This device doesn't support checking if airplane mode is on");
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to determine if airplane mod is on: " + e);
        }

        return false;
    }
}
