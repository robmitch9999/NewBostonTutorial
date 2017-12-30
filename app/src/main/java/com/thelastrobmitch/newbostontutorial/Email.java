package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Robert on 1/09/2017.
 */

public class Email extends Activity implements View.OnClickListener{

    EditText personsEmail, intro, personsName, stupidThings, hatefulAction,
        outro;
    String emailAdd, beginning, name, stupidAction, hatefulAct, out;
    Button sendEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.email);
        initialiseVars();
        sendEmail.setOnClickListener(this);
    }

    private void initialiseVars() {
        personsEmail = (EditText) findViewById(R.id.etPersonsEmail);
        intro = (EditText) findViewById(R.id.etIntro);
        personsName = (EditText) findViewById(R.id.etPersonsName);
        stupidThings = (EditText) findViewById(R.id.etStupidThings);
        hatefulAction = (EditText) findViewById(R.id.etHatefulAction);
        outro = (EditText) findViewById(R.id.etOutro);
        sendEmail = (Button) findViewById(R.id.bSendEmail);
    }

    public void onClick(View view) {

        convertEditTextsToStrings();
        String emailaddress[] = {emailAdd};
        String message = "Well hello "
                + name
                + " I just wanted to say "
                + beginning
                + ". Not only that but I hate when you "
                + stupidAction
                + ", that just really makes me crazy. I just want to make you "
                + hatefulAct
                + ". Well, that all I wnated to catter about, oh and "
                + out
                + ". Oh, and if you are an android type and bored, check out my " +
                "apps at the play store, search for Robmitch!"
                + '\n' + "PS I think I love you ...";

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL, emailaddress);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "I hate you");
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(emailIntent);

            }

    private void convertEditTextsToStrings() {
        emailAdd = personsEmail.getText().toString();
        beginning = intro.getText().toString();
        name = personsName.getText().toString();
        stupidAction = stupidThings.getText().toString();
        hatefulAct = hatefulAction.getText().toString();
        out = outro.getText().toString();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
