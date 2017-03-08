package com.android.roshan.gpacalc.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import com.android.roshan.gpacalc.models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by amilah on 19-Dec-16.
 */

public class DBHelper extends SQLiteOpenHelper{

    //database details
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gpa_calc.sqlite";
    private static final String DB_PATH_SUFFIX = "/databases/";
    static Context ctx;

    //tables
    private static final String TABLE_STUDENT = "student";
    private static final String TABLE_USER = "user";
    private static final String TABLE_SUBJECT = "subject";
    private static final String TABLE_MARKS = "marks";

    //user table details
    private static final String UID = "user_id";
    private static final String UNAME = "user_name";
    private static final String PWORD = "password";
    private static final String INDEX = "index_no";

    //student table details
    private static final String SID = "student_id";
    private static final String SNAME = "student_name";
    private static final String DOB = "dob";
    private static final String ADDRESS = "address";
    private static final String CONTACTNO = "contact_no";
    private static final String EMAIL = "email";

    //subject table details
    private static final String SUBID = "subject_id";
    private static final String SUBNAME = "subject_name";

    //marks table details
    private static final String MID = "marks_id";
    private static final String MARKS = "marks";

    // Table create statement
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + INDEX + " TEXT,"
            + UNAME + " TEXT, "
            + PWORD+ " TEXT"+" )";
    private static final String CREATE_STUDENT_TABLE = "CREATE TABLE " + TABLE_STUDENT + " ("
            + SID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SNAME + " TEXT,"
            + DOB + " DATE,"
            + ADDRESS + " TEXT,"
            + CONTACTNO + " TEXT,"
            + EMAIL + " TEXT, "
            + UID + " INTEGER,"
            + " FOREIGN KEY("+UID+") " + " REFERENCES "+TABLE_USER+"("+UID+")"+" )";
    private static final String CREATE_SUBJECT_TABLE = "CREATE TABLE " + TABLE_SUBJECT + " (" +
            SUBID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + SUBNAME + " TEXT,"
            + SID + " INTEGER,"
            + " FOREIGN KEY("+SID+") " + " REFERENCES "+TABLE_STUDENT+"("+SID+")"+" )";

    private static final String CREATE_MARKS_TABLE = "CREATE TABLE " + TABLE_MARKS + " (" +
            MID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + MARKS + " TEXT,"
            + SID + " INTEGER,"
            + SUBID + " TEXT,"
            + "CONSTRAINT uc_PersonID UNIQUE " + "("+ SUBID + "," + SID + "), "
            + " FOREIGN KEY("+SUBID+") " + " REFERENCES "+TABLE_SUBJECT+"("+SUBID+"))";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    public User Get_UserDetails() {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        if (cursor != null && cursor.moveToFirst()){
            User user = new User(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3));
            cursor.close();
            db.close();
            return user;
        }
        return null;
    }


    public void CopyDataBaseFromAsset() throws IOException {
        InputStream myInput = ctx.getAssets().open(DATABASE_NAME);
// Path to the just created empty db
        String outFileName = getDatabasePath();
// if the path doesn't exist first, create it
        File f = new File(ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX);
        if (!f.exists())
        f.mkdir();
        OutputStream myOutput = new FileOutputStream(outFileName);
// transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    private static String getDatabasePath() {
        return ctx.getApplicationInfo().dataDir + DB_PATH_SUFFIX
                + DATABASE_NAME;
    }

    public SQLiteDatabase openDataBase() throws SQLException {
        File dbFile = ctx.getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()) {
            try {
                CopyDataBaseFromAsset();
                System.out.println("Copying sucess from Assets folder");
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.CREATE_IF_NECESSARY);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
       // db.execSQL(CREATE_USER_TABLE);
//        db.execSQL(CREATE_STUDENT_TABLE);
//        db.execSQL(CREATE_MARKS_TABLE);
//        db.execSQL(CREATE_SUBJECT_TABLE);
        Log.i("Registration--",""+CREATE_MARKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_USER);
//        db.execSQL("DROP TABLE IF EXISTS" + TABLE_STUDENT);
//        db.execSQL("DROP TABLE IF EXISTS" + TABLE_SUBJECT);
//        db.execSQL("DROP TABLE IF EXISTS" + TABLE_MARKS);
        onCreate(db);
    }

    public long addUser(User u)
    {
        long response;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UNAME,u.getIndex());
        values.put(UNAME,u.getName());
        values.put(PWORD,u.getPword());
        response = db.insert(TABLE_USER,null,values);
        db.close();
        return  response;
    }
    public int updateUser(User u)
    {
        int rowcount = 0;

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(IMAGE,u.getImg());
        rowcount = db.update(TABLE_USER,values, UID + " = ?",
                new String[] { String.valueOf(u.getUid())});
        return rowcount;
    }
    public User getUser(int id)
    {
        String query = "SELECT * FROM "+ TABLE_USER + " WHERE" + UID+"="+id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(query,null);
        User u = null;
        if(c.moveToFirst())
        {
            do {
                u = new User();
                u.setIndex(c.getString(c.getColumnIndex(INDEX)));
                u.setName(c.getString(c.getColumnIndex(UNAME)));
                u.setPword(c.getString(c.getColumnIndex(PWORD)));
            }while (c.moveToNext());
        }
        return u;
    }

    public int checkUser(String uname,String pword)
    {
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.i("credentials","username2-"+uname+ "--- Password-"+pword);
        Cursor c = db.rawQuery("SELECT "+UID+" FROM "+TABLE_USER+" WHERE index_no=? AND password=? ",new String[]{uname,pword});

        if(c.getCount()>0)
        {
            c.moveToFirst();
            id = c.getInt(0);
            c.close();
        }
        return id;
    }
//
//    public ArrayList<Student> getAllStudent(int id)
//    {
//        ArrayList<Student> list = new ArrayList<Student>();
//        String select_qry = "SELECT * FROM " + TABLE_STUDENT +" WHERE "+ UID + "="+ id;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(select_qry,null);
//        if(cursor.moveToFirst())
//        {
//            do {
//                Student s = new Student();
//                s.setSid(cursor.getInt((cursor.getColumnIndex(SID))));
//                s.setSname(cursor.getString(cursor.getColumnIndex(SNAME)));
//                s.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
//                s.setDob(cursor.getString(cursor.getColumnIndex(DOB)));
//                s.setContact(cursor.getString(cursor.getColumnIndex(CONTACTNO)));
//                s.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
//                list.add(s);
//            }while (cursor.moveToNext());
//        }
//        return list;
//    }
//    public ArrayList<Student> getaStudent(int id)
//    {
//        ArrayList<Student> list = new ArrayList<Student>();
//        String select_qry = "SELECT * FROM " + TABLE_STUDENT +" WHERE "+ SID + "="+ 1;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(select_qry,null);
//        if(cursor.moveToFirst())
//        {
//            do {
//                Student s = new Student();
//                s.setSid(cursor.getInt((cursor.getColumnIndex(SID))));
//                s.setSname(cursor.getString(cursor.getColumnIndex(SNAME)));
//                s.setAddress(cursor.getString(cursor.getColumnIndex(ADDRESS)));
//                s.setDob(cursor.getString(cursor.getColumnIndex(DOB)));
//                s.setContact(cursor.getString(cursor.getColumnIndex(CONTACTNO)));
//                s.setEmail(cursor.getString(cursor.getColumnIndex(EMAIL)));
//                list.add(s);
//            }while (cursor.moveToNext());
//        }
//        return list;
//    }

//    public int updateStudent(Student s)
//    {
//        int rowcount = 0;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(SNAME,s.getSname());
//        values.put(DOB,s.getDob());
//        values.put(ADDRESS,s.getAddress());
//        values.put(CONTACTNO,s.getContact());
//        values.put(EMAIL,s.getEmail());
//        rowcount = db.update(TABLE_STUDENT,values, SID + " = ?",
//                new String[] { String.valueOf(s.getSid())});
//        return rowcount;
//    }
//    public int deleteStudent(Student s)
//    {
//        int rowDeleteCount;
//        SQLiteDatabase db = this.getWritableDatabase();
//        rowDeleteCount = db.delete(TABLE_STUDENT, SID + " = ?",
//                new String[] { String.valueOf(s.getSid()) });
//        db.close();
//        return rowDeleteCount;
//    }
//    public String getImage(int id) {
//        String b = null;
//        SQLiteDatabase db = this.getReadableDatabase();
//        try {
//            String selectQuery = "SELECT * FROM " + TABLE_USER + " WHERE " + UID + "=" + id;
//            Log.i("query--",""+selectQuery);
//            Cursor cursor = db.rawQuery(selectQuery, null);
//
//            if (cursor.getCount() > 0) {
//                while (cursor.moveToNext()) {
//                    // Convert blob data to byte array
//                    Log.i("image from db--",""+cursor.getCount());
//                    b = cursor.getString(cursor.getColumnIndex(IMAGE));
//                    // Convert the byte array to Bitmap
//                    Log.i("image from db--",""+b);
//                    return b;
//                }
//            }
//        }catch (SQLiteException e){
//            e.printStackTrace();
//        }finally {
//            db.close();
//        }
//        return b;
//    }
//    ////subject CRUD Operations
//    public int addSubject(Subject s)
//    {
//        int count = 0;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(SUBNAME,s.getSubName());
//        values.put(SID,s.getSid());
//        db.insert(TABLE_SUBJECT,null,values);
//        db.close();
//        return count;
//    }
//
//    public int deleteSubject(Subject s)
//    {
//        int rowDeleteCount;
//        SQLiteDatabase db = this.getWritableDatabase();
//        rowDeleteCount = db.delete(TABLE_SUBJECT, SUBID + " = ?",
//                new String[] { String.valueOf(s.getSid()) });
//        db.close();
//        return rowDeleteCount;
//    }
//
//    public int updateSubject(Subject s)
//    {
//        int rowcount = 0;
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(SUBNAME,s.getSubName());
//        values.put(SID,s.getSid());
//        rowcount = db.update(TABLE_STUDENT,values, SUBID + " = ?",
//                new String[] { String.valueOf(s.getSubID())});
//        return rowcount;
//    }
//    public ArrayList<Subject> getAllSubjects(int id)
//    {
//        String select_qry;
//        ArrayList<Subject> list = new ArrayList<Subject>();
//        if(id==-1)
//        {
//            select_qry = "SELECT * FROM " + TABLE_SUBJECT;
//        }
//        else
//        {
//            select_qry = "SELECT * FROM " + TABLE_SUBJECT +" WHERE "+ SID + "="+ id;
//        }
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(select_qry,null);
//        if(cursor.moveToFirst())
//        {
//            do {
//                Subject s = new Subject();
//                s.setSubID(cursor.getInt((cursor.getColumnIndex(SUBID))));
//                s.setSubName(cursor.getString(cursor.getColumnIndex(SUBNAME)));
//                s.setSid(cursor.getInt(cursor.getColumnIndex(SID)));
//                list.add(s);
//            }while (cursor.moveToNext());
//        }
//        return list;
//    }
//    public int getSubjectId(String spinnerSub)
//    {
//        int selectedSubID = -1;
//        Log.i("selectedSubject--1", "" + spinnerSub);
//        SQLiteDatabase db = this.getReadableDatabase();
////        String select_q = "SELECT "+SUBID+" FROM "+ TABLE_SUBJECT + " WHERE "+ SUBNAME + "="+""+spinnerSub+"";
//        Cursor c = db.rawQuery("SELECT "+SUBID +" FROM "+ TABLE_SUBJECT +" WHERE "+SUBNAME +"= ?; ", new String[] {spinnerSub});
//        if(c.getCount()>0)
//        {
//            c.moveToFirst();
//            selectedSubID = c.getInt(0);
//            c.close();
//        }
//        Log.i("selectedSubject--2", "" + selectedSubID);
//        return selectedSubID;
//    }
//
//    ///Marks Table CRUD Operations
//    public long addMarks(Marks m)
//    {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put(MARKS,m.getMarks());
//        values.put(SID,m.getsID());
//        values.put(SUBID,m.getSubID());
//        long count= db.insert(TABLE_MARKS,null,values);
//        db.close();
//        return count;
//    }
//
//    public int deleteMarks(int mID)
//    {
//        int rowDeleteCount;
//        SQLiteDatabase db = this.getWritableDatabase();
//        rowDeleteCount = db.delete(TABLE_MARKS, MID + " = ?",
//                new String[] { String.valueOf(mID) });
//        db.close();
//        return rowDeleteCount;
//    }
//
//    public int updateMarks(Marks m)
//    {
//        int rowcount = 0;
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//            values.put(SID,m.getsID());
//            values.put(MARKS, m.getMarks());
//            values.put(SUBID, m.getSubID());
//            Log.i("marksToUpdate--",""+m.getmID()+"--"+m.getMarks());
//            rowcount = db.update(TABLE_MARKS, values, MID + " = ?",
//                    new String[]{String.valueOf(m.getmID())});
//        return rowcount;
//    }
//    public ArrayList<Marks> getAllMarks(int sid)
//    {
//        ArrayList<Marks> list = new ArrayList<Marks>();
//        String select_qry = "SELECT * FROM " + TABLE_MARKS +" WHERE "+ SID + "="+ sid;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//
//        Cursor cursor = db.rawQuery(select_qry,null);
//        if(cursor.moveToFirst())
//        {
//            do {
//                Marks marks = new Marks();
//                marks.setmID(cursor.getInt(cursor.getColumnIndex(MID)));
//                marks.setsID(cursor.getInt((cursor.getColumnIndex(SID))));
//                marks.setMarks(cursor.getString(cursor.getColumnIndex(MARKS)));
//                marks.setSubID(cursor.getInt(cursor.getColumnIndex(SUBID)));
//                list.add(marks);
//            }while (cursor.moveToNext());
//        }
//        return list;
//    }
}
