package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * Created by Robert on 19/09/2017.
 */

public class InternalData extends Activity implements View.OnClickListener {

    EditText sharedData;
    TextView dataResults;
    FileOutputStream fos;
    String FILENAME = "InternalString";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sharedpreferences);
        setupVariables();
    }

    private void setupVariables() {
        Button save = (Button) findViewById(R.id.bSave);
        Button load = (Button) findViewById(R.id.bLoad);
        Button clear = (Button) findViewById(R.id.bClearSharedPreferences);

        sharedData = (EditText) findViewById(R.id.etSharedPrefs);
        dataResults = (TextView) findViewById(R.id.tvLoadSharedPrefs);
        save.setOnClickListener(this);
        load.setOnClickListener(this);
        clear.setOnClickListener(this);

        try {
            fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.bSave:
                String data = sharedData.getText().toString();
                // Savomg data voa File
                /*File f = new File(FILENAME);
                 *try {
                 *    fos = new FileOutputStream(f);
                 *    fos.close();
                 *} catch (FileNotFoundException e) {
                 *    e.printStackTrace();
                 *} catch (IOException e) {
                 *    e.printStackTrace();
                 *}
                 */
                try {
                    fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(data.getBytes());
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.bLoad:
                new loadSomeStuff().execute(FILENAME);

                break;
            case R.id.bClearSharedPreferences:
                String blankData = "";
                try {
                    fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                    fos.write(blankData.getBytes());
                    fos.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dataResults.setText("Load Your Data");
                sharedData.setText("");

                break;
        }
    }

    private class loadSomeStuff extends AsyncTask<String, Integer, String>{
        // in above, 1st String is the filename, Inteter is progress and
        // 2nd string is the returned item

        ProgressDialog dialog;

        protected void onPreExecute() {
            // example of setting up something
            dialog = new ProgressDialog(InternalData.this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMax(100);
            dialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {
            String collected = null;
            FileInputStream fis = null;

            for(int i = 0; i < 20; i++) {
                publishProgress(5);
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            dialog.dismiss();

            try {
                fis = openFileInput(FILENAME);
                // test for empty file, and if so, put empty message
                FileChannel channel = fis.getChannel();
                if (channel.size() == 0) {
                    // need to put warning here & recreate fis
                    try {
                        fis.close();
                        String data = "No input saved!";
                        fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
                        fos.write(data.getBytes());
                        fos.close();
                        fis = openFileInput(FILENAME);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }


                byte[] dataArray = new byte[fis.available()];
                while (fis.read(dataArray) != -1){
                    collected = new String(dataArray);

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fis != null) {
                        fis.close();
                    }
                    return collected;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            dialog.incrementProgressBy(progress[0]);

        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            dataResults.setText(result);
        }
    }
}
