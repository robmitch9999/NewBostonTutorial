package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by Robert on 26/09/2017.
 */

public class SQLiteExample extends Activity implements View.OnClickListener {

    Button sqlUpdate, sqlView, sqlModify, sqlGetInfo, sqlDelete;
    EditText sqlName, sqlHotness, sqlRow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sqliteexample);
        sqlUpdate = (Button) findViewById(R.id.bSQLUpdate);
        sqlView = (Button) findViewById(R.id.bSQLOpenView);
        sqlName = (EditText) findViewById(R.id.etSQLName);
        sqlHotness = (EditText) findViewById(R.id.etSQLHotness);
        sqlModify = (Button) findViewById(R.id.bSQLModify);
        sqlGetInfo = (Button) findViewById(R.id.bGetInfo);
        sqlDelete = (Button) findViewById(R.id.bSQLDelete);
        sqlRow = (EditText) findViewById(R.id.etRowID);

        sqlUpdate.setOnClickListener(this);
        sqlView.setOnClickListener(this);
        sqlModify.setOnClickListener(this);
        sqlGetInfo.setOnClickListener(this);
        sqlDelete.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        long line = -1;
        switch (view.getId()){
            case R.id.bSQLUpdate:

                boolean didItWork = true;

                try {

                    String name = sqlName.getText().toString();
                    String hotness = sqlHotness.getText().toString();

                    HotOrNot entry = new HotOrNot(SQLiteExample.this);
                    entry.open();
                    line = entry.createEntry(name, hotness);
                    entry.close();
                } catch (Exception e){
                    didItWork = false;
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("That'll be a fail!");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();

                } finally {
                    if (didItWork) {
                        Dialog d = new Dialog(this);
                        d.setTitle("Heck yea!");
                        TextView tv = new TextView(this);
                        tv.setText("success, line " + line);
                        d.setContentView(tv);
                        d.show();
                    }
                }

                break;
            case R.id.bSQLOpenView:
                Intent i = new Intent("com.thelastrobmitch.newbostontutorial.SQLVIEW");
                startActivity(i);
                break;
            case R.id.bGetInfo:
                try{
                    String s = sqlRow.getText().toString();
                    long l = Long.parseLong(s);
                    HotOrNot hon = new HotOrNot(this);
                    hon.open();
                    String returnedName = hon.getName(l);
                    String returnedHotness = hon.getHotness(l);
                    hon.close();

                    sqlName.setText(returnedName);
                    sqlHotness.setText(returnedHotness);
                } catch (Exception e){
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("That'll be a fail!");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                }

                break;
            case R.id.bSQLModify:
                try{
                    String sRow = sqlRow.getText().toString();
                    long lRow = Long.parseLong(sRow);
                    String mName = sqlName.getText().toString();
                    String mHotness = sqlHotness.getText().toString();

                    HotOrNot ex = new HotOrNot(this);
                    ex.open();
                    ex.updateEntry(lRow, mName, mHotness);
                    ex.close();
                } catch (Exception e){
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("That'll be a fail!");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                }
                break;

            case R.id.bSQLDelete:
                try{
                    String dRow = sqlRow.getText().toString();
                    long ldRow = Long.parseLong(dRow);
                    HotOrNot ex2 = new HotOrNot(this);
                    ex2.open();
                    ex2.deleteEntry(ldRow);
                    ex2.close();
                } catch (Exception e){
                    String error = e.toString();
                    Dialog d = new Dialog(this);
                    d.setTitle("That'll be a fail!");
                    TextView tv = new TextView(this);
                    tv.setText(error);
                    d.setContentView(tv);
                    d.show();
                }
                break;
        }
    }
}
