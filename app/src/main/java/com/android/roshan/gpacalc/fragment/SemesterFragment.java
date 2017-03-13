package com.android.roshan.gpacalc.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.adapter.HomeAdapter;
import com.android.roshan.gpacalc.adapter.SemesterAdapter;
import com.android.roshan.gpacalc.db.DBConnetion;
import com.android.roshan.gpacalc.db.DatabaseHelper;
import com.android.roshan.gpacalc.models.Semester;
import com.android.roshan.gpacalc.models.Subject;
import com.android.roshan.gpacalc.util.Constant;

import java.util.ArrayList;

/**
 * Created by amilah on 10-Mar-17.
 */

public class SemesterFragment extends ListFragment {

    View rootView;
    Semester semester;

    ListView listView;
    ArrayList<Subject> list;
    Subject subject;
    DatabaseHelper databaseHelper;
    SemesterAdapter semesterAdapter;
    TextView sem_gpa,sem_credits;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        if (bundle != null) {
            semester = new Semester();
            semester = bundle.getParcelable(Constant.BUNDLE_SEMESTER_KEY);
        }
        databaseHelper = new DatabaseHelper(getContext());
        DBConnetion.openDB(databaseHelper,getContext());
        Log.i("sem",""+semester.getId()+"--"+semester.getScredit()+""+semester.getSgpa());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.semester_fragment, container, false);

        sem_gpa = (TextView) rootView.findViewById(R.id.tv_total_gpa);
        sem_credits = (TextView) rootView.findViewById(R.id.tv_total_credits);
        float t_credits = 0;

        sem_gpa.setText(""+semester.getSgpa());

        list = databaseHelper.getListSubject(semester.getId());
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
               // f_gpa = f_gpa + list.get(i).getSub_result();
                t_credits = t_credits + list.get(i).getSub_credit();
            }
            semesterAdapter = new SemesterAdapter(getContext(), list);
            setListAdapter(semesterAdapter);
        } else {
            Toast.makeText(getContext(), "No Details", Toast.LENGTH_SHORT).show();
        }
        sem_credits.setText(""+t_credits);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = getListView();
    }
}
