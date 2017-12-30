package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SlidingDrawer;

/**
 * Created by Robert on 12/09/2017.
 */

public class Slider extends Activity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, SlidingDrawer.OnDrawerOpenListener {

    SlidingDrawer sd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sliding);

        Button handle1 = (Button) findViewById(R.id.bHandle1);
        Button handle2 = (Button) findViewById(R.id.bHandle2);
        Button handle3 = (Button) findViewById(R.id.bHandle3);
        Button handle4 = (Button) findViewById(R.id.bHandle4);
        CheckBox checkBox = (CheckBox) findViewById(R.id.cBSlidable);
        checkBox.setOnCheckedChangeListener(this);
        sd = (SlidingDrawer) findViewById(R.id.slidingD);
        sd.setOnDrawerOpenListener(this);
        handle1.setOnClickListener(this);
        handle2.setOnClickListener(this);
        handle3.setOnClickListener(this);
        handle4.setOnClickListener(this);

    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.isChecked()){
            sd.lock();

        } else {
            sd.unlock();

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bHandle1:
                sd.open();

                break;
            case R.id.bHandle2:

                break;
            case R.id.bHandle3:
                sd.toggle();

                break;
            case R.id.bHandle4:
                sd.close();

                break;
        }

    }

    @Override
    public void onDrawerOpened() {
        MediaPlayer mp = MediaPlayer.create(this, R.raw.explosion);
        mp.start();
    }
}
