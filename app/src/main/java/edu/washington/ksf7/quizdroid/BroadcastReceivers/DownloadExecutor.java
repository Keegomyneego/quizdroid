package edu.washington.ksf7.quizdroid.BroadcastReceivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class DownloadExecutor extends BroadcastReceiver {

    private static String TAG = "DownloadExecutor";

    //----------------------------------------------------------------------------------------------
    // Client Interface
    //----------------------------------------------------------------------------------------------

    public interface DownloadHandler {
        void onDownloadComplete(Context context);
    }

    public DownloadExecutor() {
    }

    public static PendingIntent getDownloadIntent(Context context, String url, DownloadHandler handler) {
        Intent downloadIntent = new Intent(context, DownloadExecutor.class);
        int callbackId = getNextHandlerId();
        handlerMap.put(callbackId, handler);

        downloadIntent.putExtra("url", url);
        downloadIntent.putExtra("callbackId", callbackId);

        return PendingIntent.getBroadcast(context, 0, downloadIntent, 0);
    }

    public static void downloadImmediately(Context context, String url, DownloadHandler handler) {

        // Start the download
        Toast.makeText(context, "Beginning download from " + url, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Beginning download from " + url);

        // Handle results
        handler.onDownloadComplete(context);
    }

    //----------------------------------------------------------------------------------------------
    // Implementation
    //----------------------------------------------------------------------------------------------

    private static Map<Integer, DownloadHandler> handlerMap = new HashMap<>();
    private static int nextHandlerId = 0;

    private static int getNextHandlerId() {
        nextHandlerId++;

        return nextHandlerId - 1;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String url = intent.getStringExtra("url");
        int callbackId = intent.getIntExtra("callbackId", -1);

        // Get the appropriate download handler
        DownloadHandler handler = handlerMap.get(callbackId);
        if (handler == null) {
            Toast.makeText(context, "Unable to find handler for url " + url, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Unable to find handler for url " + url + " and callbackId " + callbackId);
            return;
        }

        // Send sms message
//        sendSMS(context.getApplicationContext(), message, phoneNumber);

        // Start the download
        downloadImmediately(context, url, handler);
    }
}
