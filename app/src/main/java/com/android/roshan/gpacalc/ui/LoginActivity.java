package com.android.roshan.gpacalc.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.db.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class LoginActivity extends AppCompatActivity {

    ClickableSpan clickableSpan, clickableSpanP;
    private TextView footerText;

    private String username,password;
    private EditText input_username,input_password;
    private Button btnLogin;

    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

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

        footerText = (TextView)findViewById(R.id.footerText);

        SpannableString tc = new SpannableString("Don't have an account? Sign Up");
        clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(getApplicationContext(),SignUpActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
                ds.setColor(getResources().getColor(R.color.colorPrimary));
            }
        };
        tc.setSpan(new StyleSpan(Typeface.BOLD), 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tc.setSpan(clickableSpan, 23, 30, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        footerText.setText(tc);
        footerText.setMovementMethod(LinkMovementMethod.getInstance());
        footerText.setHighlightColor(Color.TRANSPARENT);


        input_username = (EditText) findViewById(R.id.input_login_username);
        input_password = (EditText) findViewById(R.id.input_login_password);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = input_username.getText().toString();
                password = input_password.getText().toString();
                Log.i("login_input-----","Username :- "+username+"& Password :- "+password);
                login(username,password);
            }
        });

    }

    private void login(String username,String password) {

        int response = dbHelper.checkUser(username,password);
        if(response== -1){
            Toast.makeText(getApplicationContext(), "Invalid Details!", Toast.LENGTH_LONG).show();
            return;
        }
        else {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

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
            Log.w("LoginActivity","DB copied");
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
