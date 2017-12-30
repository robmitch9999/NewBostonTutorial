package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by Robert on 20/10/2017.
 */



public class StatusBar extends Activity implements View.OnClickListener {

    NotificationManager nm;
    static final int uniqueID = 1111;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statusbar);
        Button stat = (Button) findViewById(R.id.bStatusBar);
        stat.setOnClickListener(this);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        nm.cancel(uniqueID);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, StatusBar.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
        String body = "This is a message, thanks for just being there";
        String title = "This is the title";

        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setSmallIcon(R.drawable.lightening_sm)
                        .setContentTitle(title)
                        .setContentText(body)
                        .setShowWhen(true)
                        .setContentIntent(pi)
                        .setDefaults(Notification.DEFAULT_ALL);
        Notification n = mBuilder.build();

        nm.notify(uniqueID, n);
        finish();
    }


}
