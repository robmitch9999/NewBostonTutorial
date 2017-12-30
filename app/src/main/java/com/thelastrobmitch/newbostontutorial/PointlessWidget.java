package com.thelastrobmitch.newbostontutorial;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by Robert on 15/10/2017.
 */


public class PointlessWidget extends AppWidgetProvider{
    int nu = 0;
    private String PREFS_NAME = "widgetSharedPreferences";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME,0);
        nu = prefs.getInt("NU",0);

        Random r = new Random();
        int randomInt =r.nextInt(5000);
        nu++;
        String rand = String.valueOf(randomInt) + " n= " + String.valueOf(nu);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("NU", nu).apply();

        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++){
            int awID = appWidgetIds[i];
            RemoteViews v = new RemoteViews(context.getPackageName(), R.layout.widget);
            v.setTextViewText(R.id.tvWidgetUpdate, rand);
            appWidgetManager.updateAppWidget(awID, v);
        }
    }


    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        Toast.makeText(context, "I have gone now", Toast.LENGTH_SHORT).show();
    }


}
