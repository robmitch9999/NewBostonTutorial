package com.thelastrobmitch.newbostontutorial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static java.lang.String.format;

/**
 * Created by Robert on 26/09/2017.
 */

public class HotOrNot {

    public static final String KEY_ROWID = "id";
    public static final String KEY_NAME = "persons_name";
    public static final String KEY_HOTNESS = "persons_hotness";

    private static final String DATABASE_NAME = "HotOrNotdb";
    private static final String DATABASE_TABLE = "peopleTable";
    private static final int DATABASE_VERSION = 1;

    private DbHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public String getData() {
        String[] columns = new String[] {KEY_ROWID, KEY_NAME, KEY_HOTNESS};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, null, null, null, null, null);
        String result = "";

        int iRow = c.getColumnIndex(KEY_ROWID);
        int iName = c.getColumnIndex(KEY_NAME);
        int iHotness = c.getColumnIndex(KEY_HOTNESS);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
            result = result
//                    + c.getString(iRow) + "     "
//                    + c.getString(iName) + "       "
//                    + c.getString(iHotness) + "\n";
            + String.format("%3s%-30s%10s", c.getString(iRow)+ " ",
                    c.getString(iName), c.getString(iHotness)) + "\n";
        }


        return result;
    }

    public String getName(long l) {
        String[] columns = new String[] {KEY_ROWID, KEY_NAME, KEY_HOTNESS};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
        if(c != null) {
            c.moveToFirst();
            String name = c.getString(1);
            return name;
        }
        return null;
    }

    public String getHotness(long l) throws SQLException{
        String[] columns = new String[] {KEY_ROWID, KEY_NAME, KEY_HOTNESS};
        Cursor c = ourDatabase.query(DATABASE_TABLE, columns, KEY_ROWID + "=" + l, null, null, null, null);
        if(c != null) {
            c.moveToFirst();
            String hotness = c.getString(2);
            return hotness;
        }
        return null;
    }

    public void updateEntry(long lRow, String mName, String mHotness) throws SQLException{
        ContentValues cvUpdate = new ContentValues();
        cvUpdate.put(KEY_NAME, mName);
        cvUpdate.put(KEY_HOTNESS, mHotness);
        ourDatabase.update(DATABASE_TABLE, cvUpdate, KEY_ROWID + "=" + lRow, null);
    }

    public void deleteEntry(long ldRow) throws SQLException{
        ourDatabase.delete(DATABASE_TABLE,KEY_ROWID + "=" + ldRow, null);
    }


    private static class DbHelper extends SQLiteOpenHelper{

        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    KEY_NAME + " TEXT NOT NULL, " +
                    KEY_HOTNESS + " TEXT NOT NULL);"
            );

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public HotOrNot(Context c) {
        this.ourContext = c;
    }

    public HotOrNot open() throws SQLException{
        ourHelper = new DbHelper(ourContext);
        ourDatabase = ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        ourHelper.close();
    }

    public long createEntry(String name, String hotness) {
        ContentValues cv = new ContentValues();
        cv.put(KEY_NAME, name);
        cv.put(KEY_HOTNESS, hotness);
        return ourDatabase.insert(DATABASE_TABLE, null, cv);
    }
}
