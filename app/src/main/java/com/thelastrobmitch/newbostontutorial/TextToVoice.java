package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

/**
 * Created by Robert on 19/10/2017.
 */

public class TextToVoice extends Activity implements View.OnClickListener {

    String text;
    EditText textToRead;
    TextToSpeech tts;

    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.texttovoice);
        Button b= (Button)findViewById(R.id.bTextToVoice);
        textToRead = (EditText) findViewById(R.id.etTextToRead);
        b.setOnClickListener(this);
        tts = new TextToSpeech(TextToVoice.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.FRANCE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        text = textToRead.getText().toString();
        tts.speak(text,TextToSpeech.QUEUE_FLUSH, null, null);

    }

    @Override
    protected void onPause() {
        if (tts != null){
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }
}
