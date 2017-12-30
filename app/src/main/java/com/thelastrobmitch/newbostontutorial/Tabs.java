package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * Created by Robert on 16/09/2017.
 */

public class Tabs extends Activity implements View.OnClickListener {

    TabHost th;
    TextView showResults;
    long start, stop;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabs);
        th = (TabHost) findViewById(R.id.tabHost);
        Button newTab = (Button) findViewById(R.id.bAddTab);
        Button bStart = (Button) findViewById(R.id.bStartWatch);
        Button bStop = (Button) findViewById(R.id.bStopWatch);
        showResults = (TextView) findViewById(R.id.tvShowResults);

        bStart.setOnClickListener(this);
        bStop.setOnClickListener(this);


        newTab.setOnClickListener(this);

        th.setup();
        TabHost.TabSpec specs = th.newTabSpec("tag1");
        specs.setContent(R.id.tab1);
        specs.setIndicator("Stop Watch");
        th.addTab(specs);
        specs = th.newTabSpec("tag2");
        specs.setContent(R.id.tab2);
        specs.setIndicator("Tab 2");
        th.addTab(specs);
        specs = th.newTabSpec("tag3");
        specs.setContent(R.id.tab3);
        specs.setIndicator("Add a Tab");
        th.addTab(specs);
        start = 0;

    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bAddTab:

                TabHost.TabSpec ourSpec = th.newTabSpec("tag 1");
                ourSpec.setContent(new TabHost.TabContentFactory() {
                    @Override
                    public View createTabContent(String s) {

                        TextView text = new TextView(Tabs.this);
                        text.setText("You've created this new tab with this TextView in it!");
                        return (text);
                    }
                });
                ourSpec.setIndicator("New Tab");
                th.addTab(ourSpec);

                break;
            case R.id.bStartWatch:
                start = System.currentTimeMillis();

                break;
            case R.id.bStopWatch:
                stop = System.currentTimeMillis();
                if(start != 0){
                    long result = (stop - start);
                    int millis = (int) result;
                    int seconds = (int) result/1000;
                    int minutes = seconds/60;
                    millis = millis % 1000;
                    seconds = seconds % 60;

                    showResults.setText(String.format("%d:%02d:%03d",minutes, seconds, millis));
                }


                break;

        }
    }
}
