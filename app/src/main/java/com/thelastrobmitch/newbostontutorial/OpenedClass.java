package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Robert on 3/09/2017.
 */

public class OpenedClass extends Activity
        implements View.OnClickListener, RadioGroup.OnCheckedChangeListener{

    TextView question, test;
    Button returnData;
    RadioGroup answers;
    String gotBread, sendData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send);
        initialise();

        SharedPreferences getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        String et = getData.getString("name", "Robert is ... ");
        String values = getData.getString("list", "4");
        if (values.contentEquals("1")){
            question.setText(et);
        }


//        Bundle gotBasket = getIntent().getExtras();
//        gotBread = gotBasket.getString("key");
//        question.setText(gotBread);
    }

    private void initialise() {
        question = (TextView) findViewById(R.id.tvQuestion);
        answers = (RadioGroup) findViewById(R.id.rgAnswers);
        RadioButton crazy = (RadioButton) findViewById(R.id.rbCrazy);
        RadioButton sexy = (RadioButton) findViewById(R.id.rbSexy);
        RadioButton both = (RadioButton) findViewById(R.id.rbBoth);
        returnData = (Button) findViewById(R.id.bReturn);
        test = (TextView) findViewById(R.id.tvTest);
        returnData.setOnClickListener(this);
        answers.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent person = new Intent();
        Bundle backpack = new Bundle();
        backpack.putString("answer", sendData);
        person.putExtras(backpack);
        setResult(RESULT_OK, person);
        finish();
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

        switch (i){
            case R.id.rbCrazy:
                sendData = "Crazy? ... Probably right";
                break;
            case R.id.rbSexy:
                sendData = "Super Sexy? ... Definately right";
                break;
            case R.id.rbBoth:
                sendData = "Crazy & Sexy? ... Absolutely";
                break;
        }
        test.setText(sendData);
    }
}
