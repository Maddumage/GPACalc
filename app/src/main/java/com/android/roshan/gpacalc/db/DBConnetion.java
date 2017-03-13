package com.android.roshan.gpacalc.db;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by amilah on 13-Mar-17.
 */

public class DBConnetion {

    public static void openDB(DatabaseHelper databaseHelper,Context context){
        File database = context.getDatabasePath(DatabaseHelper.DBNAME);
        if (false == database.exists()) {
            databaseHelper.getReadableDatabase();
            //Copy db
            if (copyDatabase(context)) {
                Toast.makeText(context, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }

    private static boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[] buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("LoginActivity", "DB copied");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
