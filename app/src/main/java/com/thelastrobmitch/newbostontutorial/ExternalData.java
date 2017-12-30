package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Robert on 19/09/2017.
 */

public class ExternalData extends Activity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private TextView canRead, canWrite;
    private String state;
    boolean canW, canR;
    Spinner spinner;
    String[] paths = {"Music", "Pictures" , "Downloads"};
    File path = null;
    File file = null;
    EditText saveFile;
    Button confirm, save;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.externaldata);
        canRead = (TextView) findViewById(R.id.tvCanRead);
        canWrite = (TextView) findViewById(R.id.tvCanWrite);
        spinner = (Spinner) findViewById(R.id.spinner);
        confirm = (Button) findViewById(R.id.bConfirmSaveAs);
        save = (Button) findViewById(R.id.bSaveFile);
        saveFile = (EditText) findViewById(R.id.etSaveAs);
        confirm.setOnClickListener(this);
        save.setOnClickListener(this);

        checkState();
        


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ExternalData.this,
                android.R.layout.simple_spinner_item, paths);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void checkState() {
        state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            //read and write
            canWrite.setText("Can Write to External Storage");
            canRead.setText("Can Read from External Storage");
            canW = canR = true;
        } else if (state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)){
            //read but cannot write
            canWrite.setText("Cannot Write to External Storage");
            canRead.setText("Can Read from External Storage");
            canW = false;
            canR = true;
        } else {
            canWrite.setText("Cannot Write to External Storage");
            canRead.setText("Cannot Write to External Storage");
            canW = canR = false;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        int position = spinner.getSelectedItemPosition();
        switch (position){
            case 0:
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);
                break;
            case 1:
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                break;
            case 2:
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                break;
            default:
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {
        switch ( (view.getId())){
            case R.id.bSaveFile:
                String f = saveFile.getText().toString();
                file = new File(path, f);

                checkState();
                if ((canW) && (canR)){

// following line creates directory at path if it doesn't yet exist
                    path.mkdirs();

                    try {
                        InputStream is = getResources().openRawResource
                                (R.raw.splash2);
                        byte[] data = new byte[is.available()];
                        is.read(data);

                        OutputStream os = new FileOutputStream(file);
                        os.write(data);
                        is.close();
                        os.close();
                        Toast t = Toast.makeText(ExternalData.this, "File has been saved" + path,
                                Toast.LENGTH_LONG);
                        t.show();

                        // update media listing so that the user can find them in for example, gallery.

                        MediaScannerConnection.scanFile(ExternalData.this, new String[] {file.toString()},
                                null,
                                new MediaScannerConnection.OnScanCompletedListener(){
                                    public void onScanCompleted(String path, Uri uri){
                                        Toast t = Toast.makeText(ExternalData.this, "scan complete",
                                                Toast.LENGTH_LONG);
                                        t.show();
                                    }
                                });
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.bConfirmSaveAs:
                save.setVisibility(View.VISIBLE);

                break;
        }
    }
}
