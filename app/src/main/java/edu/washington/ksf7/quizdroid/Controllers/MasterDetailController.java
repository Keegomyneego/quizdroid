package edu.washington.ksf7.quizdroid.Controllers;

import android.content.Intent;
import android.util.Log;

/**
 * Created by keegomyneego on 1/30/17.
 */

public class MasterDetailController {

    private static final String TAG = "MasterDetailController";

    public static final String DETAIL_POSITION_INTENT_KEY = "MasterDetail.DetailPosition";

    public static void setDetailPosition(Intent intent, int position) {
        intent.putExtra(DETAIL_POSITION_INTENT_KEY, position);
    }

    public static int getDetailPosition(Intent intent) {
        int position = intent.getIntExtra(DETAIL_POSITION_INTENT_KEY, -1);
        if (position == -1) {
            Log.e(TAG, "No position found in intent!");
        }

        return position;
    }
}
