package edu.washington.ksf7.quizdroid.BroadcastReceivers;

import android.Manifest;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import edu.washington.ksf7.quizdroid.Repositories.TopicRepository;

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

    public static void downloadImmediately(final Context context, String url, final DownloadHandler handler) {

        Toast.makeText(context, "Beginning download from " + url, Toast.LENGTH_SHORT).show();

        // Call handler after download completes
        FileDownload download = new FileDownload() {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                handler.onDownloadComplete(context);
            }
        };

        // Start the download
        download.execute(url);

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

        // Start the download
        downloadImmediately(context, url, handler);
    }

    private static class FileDownload extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... fileURL) {
            Log.i(TAG, "Beginning download from " + fileURL[0]);

            try {
                URL url = new URL(fileURL[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                InputStream input = new BufferedInputStream(
                        url.openStream()
                );

                OutputStream output = new FileOutputStream(
                        Environment.getExternalStorageDirectory().toString() + "/" + TopicRepository.QUESTIONS_FILE_NAME
                );

                int fileSize = connection.getContentLength();
                byte[] buffer = new byte[1024]; // 1 KB buffer
                int bytesRead;
                int totalBytesRead = 0;

                while ((bytesRead = input.read(buffer)) > 0) {
                    // write to file
                    output.write(buffer, 0, bytesRead);

                    // give progress updates
                    totalBytesRead += bytesRead;
                    publishProgress("" + (totalBytesRead * 100 / fileSize));
                }

                // clean up
                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            Log.i(TAG, "Download progress: " + values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i(TAG, "Download complete");
        }
    }
}
