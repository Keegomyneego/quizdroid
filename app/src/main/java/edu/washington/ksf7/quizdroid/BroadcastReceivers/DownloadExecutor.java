package edu.washington.ksf7.quizdroid.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

public class DownloadExecutor extends BroadcastReceiver {

    private static String TAG = "AWTYBroadcastReceiver";


    //----------------------------------------------------------------------------------------------
    // Client Interface
    //----------------------------------------------------------------------------------------------

    public DownloadExecutor() {
    }

    //----------------------------------------------------------------------------------------------
    // Implementation
    //----------------------------------------------------------------------------------------------

    private static ExecutionStatus executionStatus = ExecutionStatus.STOPPED;
    private static AlarmManager alarmManager;
    private static PendingIntent alarmIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = intent.getStringExtra("phoneNumber");
        String message = intent.getStringExtra("message");

        // Send sms message
        sendSMS(context.getApplicationContext(), message, phoneNumber);

        // Notify sender that it's been sent
        Toast.makeText(context, "Texting " + phoneNumber + ": " + message, Toast.LENGTH_SHORT).show();

        // Log that we sent it
        Log.i(TAG, "Sending \"" + message + "\" to " + phoneNumber);
    }

    private void sendSMS(Context context, String message, String phoneNumber) {

        // Listen to status broadcasts
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";
        context.registerReceiver(getSMSSentStatusReceiver(), new IntentFilter(SENT));
        context.registerReceiver(getSMSDeliveredStatusReceiver(), new IntentFilter(DELIVERED));

        // Send the message
        SmsManager smsManager = SmsManager.getDefault();
        PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, new Intent(SENT), 0);
        PendingIntent deliveredPI = PendingIntent.getBroadcast(context, 0, new Intent(DELIVERED), 0);
        smsManager.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    // BroadcastReceivers

    private BroadcastReceiver getSMSSentStatusReceiver() {
        return new BroadcastReceiver() {

            @Override
            public void onReceive(Context msgContext, Intent msgIntent) {
                String sentStatus = "";

                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        sentStatus = "SMS sent";
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        sentStatus = "Generic failure";
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        sentStatus = "No service";
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        sentStatus = "Null PDU";
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        sentStatus = "Radio off";
                        break;
                }

                Toast.makeText(msgContext, sentStatus, Toast.LENGTH_SHORT).show();

                // One time receiver, stop listening after first receive
                msgContext.unregisterReceiver(this);
            }
        };
    }

    private BroadcastReceiver getSMSDeliveredStatusReceiver() {
        return new BroadcastReceiver() {

            @Override
            public void onReceive(Context msgContext, Intent msgIntent) {
                String deliveredStatus = "";

                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        deliveredStatus = "SMS delivered";
                        break;
                    case Activity.RESULT_CANCELED:
                        deliveredStatus = "SMS not delivered";
                        break;
                }

                Toast.makeText(msgContext, deliveredStatus, Toast.LENGTH_SHORT).show();

                // One time receiver, stop listening after first receive
                msgContext.unregisterReceiver(this);
            }
        };
    }
}
