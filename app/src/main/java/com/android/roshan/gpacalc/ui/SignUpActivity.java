package com.android.roshan.gpacalc.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.db.DatabaseHelper;
import com.android.roshan.gpacalc.models.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class SignUpActivity extends AppCompatActivity {

    private EditText input_index, input_fullname, input_pword;
    private Button btn_register;
    DatabaseHelper dbHelper;
    String index, fullname, pword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dbHelper = new DatabaseHelper(this);
        //Check exists database
        File database = getApplicationContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()) {
            dbHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(this)) {
                Toast.makeText(this, "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        input_index = (EditText) findViewById(R.id.input_index);
        input_fullname = (EditText) findViewById(R.id.input_full_name);
        input_pword = (EditText) findViewById(R.id.input_password);

        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                index = input_index.getText().toString();
                fullname = input_fullname.getText().toString();
                pword = input_pword.getText().toString();
                if (isInputValid(index,fullname,pword)) {
                    User user = new User();
                    user.setIndex(index);
                    user.setPword(pword);
                    user.setName(fullname);
                    if(dbHelper.addUser(user) != -1){
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        Toast.makeText(getApplicationContext(),"Registration Success!",Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Registration Failed!",Toast.LENGTH_LONG).show();
                        return;
                    }

                }
            }
        });
    }

    private boolean isInputValid(String index,String fname, String pword) {

        if (pword==null && fname==null && pword==null && index.length() != 7) {
            input_index.setText("");
            input_fullname.setText("");
            input_pword.setText("");
            setError();
            return false;
        } else

        return true;
    }

    private void setError() {
        Toast.makeText(getApplicationContext(), "Invalid Details!", Toast.LENGTH_LONG).show();
    }
    private boolean copyDatabase(Context context) {
        try {

            InputStream inputStream = context.getAssets().open(DatabaseHelper.DBNAME);
            String outFileName = DatabaseHelper.DBLOCATION + DatabaseHelper.DBNAME;
            OutputStream outputStream = new FileOutputStream(outFileName);
            byte[]buff = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            Log.w("SignUpActivity","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
