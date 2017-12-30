package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by Robert on 3/09/2017.
 */

public class Data extends Activity implements View.OnClickListener{

    EditText sendET;
    Button startFor, start;
    TextView gotAnswer;
    RelativeLayout rl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get);
        initialise();

        rl = (RelativeLayout) findViewById(R.id.relLayout);


        // the advertisement stuff below does seem to work ...
        MobileAds.initialize(Data.this, getString(R.string.ad_unit));
        AdView ad = new AdView(Data.this, null, 0);
        ad.setAdUnitId(getString(R.string.ad_unit));
        ad.setAdSize(AdSize.BANNER);
        // adjust the layout parameters:
        RelativeLayout.LayoutParams adViewParams = new RelativeLayout.LayoutParams(
                AdView.LayoutParams.WRAP_CONTENT,
                AdView.LayoutParams.WRAP_CONTENT);
        adViewParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        adViewParams.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);

        // add the ad to the relative layout
        rl.addView(ad, adViewParams);
        // request the ad content
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        ad.loadAd(adRequest);
    }

    private void initialise() {
        sendET = (EditText) findViewById(R.id.etSend);
        startFor = (Button) findViewById(R.id.bSAFR);
        start = (Button) findViewById(R.id.bSA);
        gotAnswer = (TextView) findViewById(R.id.tvGot);
        start.setOnClickListener(this);
        startFor.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle basket = data.getExtras();
            String s = basket.getString("answer");
            gotAnswer.setText(s);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bSA:
                String bread = sendET.getText().toString();
                Bundle basket = new Bundle();
                basket.putString("key", bread);
                Intent a = new Intent(Data.this, OpenedClass.class);
                a.putExtras(basket);
                startActivity(a);
                break;
            case R.id.bSAFR:
                Intent i = new Intent(Data.this, OpenedClass.class);
                startActivityForResult(i, 0);

                break;
        }
    }
}
