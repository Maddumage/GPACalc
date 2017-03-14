package com.android.roshan.gpacalc.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.db.DBConnetion;
import com.android.roshan.gpacalc.db.DatabaseHelper;
import com.android.roshan.gpacalc.models.Semester;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by amilah on 14-Mar-17.
 */

public class Graph extends Fragment{

    View rootView;
    private LineChart mChart;
    protected Typeface mTfLight;
    ArrayList<Semester> semesterList;
    DatabaseHelper databaseHelper;
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
        rootView = inflater.inflate(R.layout.graph_fragment,container,false);

        semesterList = databaseHelper.getListSemester();
        if (semesterList.size() > 0) {

            mChart = (LineChart) rootView.findViewById(R.id.chart1);
            mChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
            mChart.getXAxis().setLabelsToSkip(0);
            mChart.getAxisLeft().setLabelCount(10,false);
            mChart.getAxisRight().setLabelCount(10,false);
            ArrayList<Entry> entries = new ArrayList<>();
            ArrayList<String> labels = new ArrayList<String>();
            for (int i = 0; i < semesterList.size(); i++) {
                //yVals.add(new Entry(semesters.get(i).getSgpa()),i);
                float val = semesterList.get(i).getSgpa();

                entries.add(new Entry(val, i));
                labels.add("Sem "+semesterList.get(i).getId());
            }

            LineDataSet dataset = new LineDataSet(entries, "# of Calls");

            LineData data = new LineData(labels, dataset);
            dataset.setColors(ColorTemplate.COLORFUL_COLORS); //
            dataset.setDrawCubic(true);
            dataset.setDrawFilled(false);

            mChart.setData(data);
           // mChart.animateY(3000);
            mChart.animateX(1000);
        }
        return rootView;
    }
}
