package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;

/**
 * Created by Robert on 5/09/2017.
 */

public class Graphics extends Activity{

    PowerManager.WakeLock wL;

    MyBringBack ourView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        // set up wake lock
        PowerManager pM = (PowerManager)getSystemService(Context.POWER_SERVICE);
        wL = pM.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "wakeTag");


        super.onCreate(savedInstanceState);
        wL.acquire();
        ourView = new MyBringBack(this);
        setContentView(ourView);

    }

    @Override
    protected void onPause() {
        super.onPause();
        wL.release();
    }
}
