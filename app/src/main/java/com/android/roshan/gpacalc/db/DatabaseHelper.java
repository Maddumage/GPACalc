package com.android.roshan.gpacalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.android.roshan.gpacalc.models.Semester;
import com.android.roshan.gpacalc.models.Subject;
import com.android.roshan.gpacalc.models.User;
import com.android.roshan.gpacalc.util.Constant;

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
    ///////Semester
    public long addSemester(Semester semester) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sgpa", semester.getSgpa());
        contentValues.put("s_credits", semester.getScredit());
        openDatabase();
        long returnValue = mDatabase.insert("semester", null, contentValues);
        closeDatabase();
        return returnValue;
    }
    public boolean deleteSemesterById(int id) {
        openDatabase();
        int result = mDatabase.delete("semester",  "id =?", new String[]{String.valueOf(id)});
        closeDatabase();
        return result !=0;
    }
    public long updateSemester(Semester semester) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("sgpa", semester.getSgpa());
        contentValues.put("s_credits", semester.getScredit());
        String[] whereArgs = {Integer.toString(semester.getId())};
        openDatabase();
        long returnValue = mDatabase.update("semester",contentValues, "id=?", whereArgs);
        closeDatabase();
        return returnValue;
    }
    public ArrayList<Semester> getListSemester() {
        Semester semester = null;
        ArrayList<Semester> list = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM semester", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            semester = new Semester(cursor.getInt(0), cursor.getFloat(1),cursor.getFloat(2));
            list.add(semester);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return list;
    }

    /////////////subjects

    public long addSubject(Subject subject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.TABLE_SUBJECT_COL_SUB_CODE, subject.getSub_code());
        contentValues.put(Constant.TABLE_SUBJECT_COL_SUB_NAME, subject.getSub_name());
        contentValues.put(Constant.TABLE_SUBJECT_COL_SUB_CREDIT, subject.getSub_credit());
        contentValues.put(Constant.TABLE_SUBJECT_COL_SUB_RESULT, subject.getSub_credit());
        contentValues.put(Constant.TABLE_SUBJECT_COL_SEM_ID, subject.getSem_id());
        openDatabase();
        long returnValue = mDatabase.insert(Constant.TABLE_NAME_SUBJECT, null, contentValues);
        closeDatabase();
        return returnValue;
    }
    public boolean deleteSubjectById(int id) {
        openDatabase();
        int result = mDatabase.delete(Constant.TABLE_NAME_SUBJECT,  "id =?", new String[]{String.valueOf(id)});
        closeDatabase();
        return result !=0;
    }
    public long updateSubject(Subject subject) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(Constant.TABLE_SUBJECT_COL_SUB_CODE, subject.getSub_code());
        contentValues.put(Constant.TABLE_SUBJECT_COL_SUB_NAME, subject.getSub_name());
        contentValues.put(Constant.TABLE_SUBJECT_COL_SUB_CREDIT, subject.getSub_credit());
        contentValues.put(Constant.TABLE_SUBJECT_COL_SUB_RESULT, subject.getSub_credit());
        contentValues.put(Constant.TABLE_SUBJECT_COL_SEM_ID, subject.getSem_id());
        String[] whereArgs = {Integer.toString(subject.getId())};
        openDatabase();
        long returnValue = mDatabase.update(Constant.TABLE_NAME_SUBJECT,contentValues, "id=?", whereArgs);
        closeDatabase();
        return returnValue;
    }
    public ArrayList<Subject> getListSubject(int id) {
        Subject subject = null;
        ArrayList<Subject> list = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM subject WHERE sem_id="+id, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            subject = new Subject(cursor.getInt(0), cursor.getString(1),cursor.getString(2),cursor.getInt(3),cursor.getFloat(4),cursor.getString(5));
            list.add(subject);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return list;
    }
}
