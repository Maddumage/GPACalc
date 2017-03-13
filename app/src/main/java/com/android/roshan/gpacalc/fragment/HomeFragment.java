package com.android.roshan.gpacalc.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.adapter.HomeAdapter;
import com.android.roshan.gpacalc.db.DBConnetion;
import com.android.roshan.gpacalc.db.DatabaseHelper;
import com.android.roshan.gpacalc.models.Semester;
import com.android.roshan.gpacalc.util.Constant;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends ListFragment {

    View rootView;
    ArrayList<Semester> semesterList;
    DatabaseHelper databaseHelper;
    Semester semester;
    HomeAdapter homeAdapter;
    ListView listView;
    TextView final_gpa, total_credits;
    float f_gpa, t_credits;
    FragmentTransaction fragmentTransaction;
    SemesterFragment semesterFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getContext());
        //Check exists database
        DBConnetion.openDB(databaseHelper,getContext());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.home_fragment, container, false);
        final_gpa = (TextView) rootView.findViewById(R.id.tv_total_gpa);
        total_credits = (TextView) rootView.findViewById(R.id.tv_total_credits);

        semesterList = databaseHelper.getListSemester();
        if (semesterList.size() > 0) {
            for (int i = 0; i < semesterList.size(); i++) {
                f_gpa = f_gpa + semesterList.get(i).getSgpa();
                t_credits = t_credits + semesterList.get(i).getScredit();
            }
            homeAdapter = new HomeAdapter(getContext(), semesterList);
            setListAdapter(homeAdapter);
        } else {
            Toast.makeText(getContext(), "No Details", Toast.LENGTH_SHORT).show();
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String avg_gpa = df.format(f_gpa / semesterList.size());
        final_gpa.setText("" + avg_gpa);
        total_credits.setText("" + t_credits);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getListView();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                semester = semesterList.get(position);
                bundle.putParcelable(Constant.BUNDLE_SEMESTER_KEY,semester);
                fragmentTransaction = getFragmentManager().beginTransaction();
                semesterFragment = new SemesterFragment();
                semesterFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.content_main,semesterFragment).commit();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                alert.setTitle("Delete");
                alert.setMessage("Do you want to delete this item?");
                alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // main code on after clicking yes\
                        semesterList.remove(position);
                        boolean response = databaseHelper.deleteSemesterById(semesterList.get(position).getId());
                        homeAdapter.notifyDataSetChanged();
                        if(response)
                        {
                            Toast.makeText(getContext(),"Item Removed",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getContext(),"Item Not Removed",Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
                return true;
            }
        });


    }


}
