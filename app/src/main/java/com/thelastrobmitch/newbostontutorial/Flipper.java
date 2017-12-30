package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ViewFlipper;

/**
 * Created by Robert on 18/09/2017.
 */

public class Flipper extends Activity implements View.OnClickListener {

    ViewFlipper flippy;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flipper);

        flippy = (ViewFlipper) findViewById(R.id.vf);
        flippy.setOnClickListener(this);
        flippy.setFlipInterval(500);
        flippy.startFlipping();
    }

    @Override
    public void onClick(View view) {
        flippy.showNext();
    }
}
