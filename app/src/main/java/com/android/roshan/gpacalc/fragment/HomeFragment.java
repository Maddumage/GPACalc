package com.android.roshan.gpacalc.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.adapter.HomeAdapter;
import com.android.roshan.gpacalc.db.DatabaseHelper;
import com.android.roshan.gpacalc.models.Semester;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by amilah on 08-Mar-17.
 */

public class HomeFragment extends ListFragment {

    View rootView;
    ArrayList<Semester> semesterList;
    DatabaseHelper databaseHelper;
    Semester semester;
    HomeAdapter homeAdapter;
    ListView listView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        //Check exists database
        File database = getContext().getDatabasePath(DatabaseHelper.DBNAME);
        if(false == database.exists()) {
            databaseHelper.getReadableDatabase();
            //Copy db
            if(copyDatabase(getContext())) {
                Toast.makeText(getContext(), "Copy database succes", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Copy data error", Toast.LENGTH_SHORT).show();
                return;
            }
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.home_fragment,container,false);

        semesterList = databaseHelper.getListSemester();
        if (semesterList.size()>0) {
            homeAdapter = new HomeAdapter(getContext(), semesterList);

            setListAdapter(homeAdapter);
        }
        else
            Toast.makeText(getContext(),"No Details",Toast.LENGTH_SHORT).show();


        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getListView();
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
