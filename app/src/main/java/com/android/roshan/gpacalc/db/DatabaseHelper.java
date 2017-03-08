package com.android.roshan.gpacalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.roshan.gpacalc.models.User;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NgocTri on 11/7/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "gpaCalc.sqlite";
    public static final String DBLOCATION = "/data/data/com.android.roshan.gpacalc/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public List<User> getListUser() {
        User user = null;
        List<User> users = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM user", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
            users.add(user);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return users;
    }

    public User getUserById(int id) {
        User user = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM user WHERE id = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3));
        //Only 1 resul
        cursor.close();
        closeDatabase();
        return user;
    }
    public long updateUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("index_no", user.getIndex());
        contentValues.put("full_name", user.getName());
        contentValues.put("password", user.getPword());
        String[] whereArgs = {Integer.toString(user.getUid())};
        openDatabase();
        long returnValue = mDatabase.update("user",contentValues, "id=?", whereArgs);
        closeDatabase();
        return returnValue;
    }

    public long addUser(User user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("index_no", user.getIndex());
        contentValues.put("full_name", user.getName());
        contentValues.put("password", user.getPword());
        openDatabase();
        long returnValue = mDatabase.insert("user", null, contentValues);
        closeDatabase();
        return returnValue;
    }
    public boolean deleteUserById(int id) {
        openDatabase();
        int result = mDatabase.delete("user",  "id =?", new String[]{String.valueOf(id)});
        closeDatabase();
        return result !=0;
    }
    public int checkUser(String uname,String pword)
    {
        int id = -1;
        openDatabase();
        Log.i("credentials","index-"+uname+ "--- Password-"+pword);
        Cursor c = mDatabase.rawQuery("SELECT id FROM user WHERE index_no=? AND password=? ",new String[]{uname,pword});
        if(c.getCount()>0)
        {
            c.moveToFirst();
            id = c.getInt(0);
            c.close();
        }
        closeDatabase();
        return id;
    }
}
