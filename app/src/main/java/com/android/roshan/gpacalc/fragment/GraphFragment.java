package com.android.roshan.gpacalc.fragment;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.adapter.HomeAdapter;
import com.android.roshan.gpacalc.db.DBConnetion;
import com.android.roshan.gpacalc.db.DatabaseHelper;
import com.android.roshan.gpacalc.models.Semester;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

/**
 * Created by amilah on 14-Mar-17.
 */

public class GraphFragment extends Fragment{

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

            mChart = (LineChart)rootView.findViewById(R.id.chart1);
            mChart.setViewPortOffsets(0, 0, 0, 0);
            mChart.setBackgroundColor(Color.WHITE);

            // no description text
//            mChart.getDescription().setEnabled(true);

            // enable touch gestures
            mChart.setTouchEnabled(true);

            // enable scaling and dragging
            mChart.setDragEnabled(false);
            mChart.setScaleEnabled(false);

            // if disabled, scaling can be done on x- and y-axis separately
            mChart.setPinchZoom(true);

            mChart.setDrawGridBackground(true);
//            mChart.setMaxHighlightDistance(500);
//
//            XAxis x = mChart.getXAxis();
//            x.setEnabled(true);
//            // setting position to TOP and INSIDE the chart
//            x.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
//            //  setting text size for our axis label
//            x.setTextSize(10f);
//            x.setTextColor(Color.BLACK);
//
//            // to draw axis line
//            x.setDrawAxisLine(true);
//            x.setDrawGridLines(false);
//
//            YAxis y = mChart.getAxisLeft();
//            y.setTypeface(mTfLight);
//            y.setLabelCount(10, false);
////            y.setDrawZeroLine(true);
////            y.setZeroLineColor(Color.GRAY);
//            y.setTextColor(Color.GRAY);
//            y.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
//            y.setDrawGridLines(true);
//            y.setAxisLineColor(Color.GRAY);

            mChart.getAxisRight().setEnabled(false);

            // add data
            setData(semesterList);

            mChart.getLegend().setEnabled(false);

            mChart.animateXY(2000, 2000);

            // dont forget to refresh the drawing
            mChart.invalidate();
        } else {
            Toast.makeText(getContext(), "No Details", Toast.LENGTH_SHORT).show();
        }


        return rootView;
    }

    private void setData(ArrayList<Semester> semesters) {

        ArrayList<Entry> yVals = new ArrayList<Entry>();
        Semester semester;
        for (int i = 0; i < semesters.size(); i++) {
            //yVals.add(new Entry(semesters.get(i).getSgpa()),i);
            float val = semesterList.get(i).getSgpa();
            yVals.add(new Entry(val, i));
        }
        ArrayList<String> labels = new ArrayList<String>();
        for (int i=0;i<semesterList.size();i++){
            labels.add("Sem "+semesterList.get(i).getId());
        }
        LineDataSet set1;

        if (mChart.getData() != null &&
                mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet)mChart.getData().getDataSetByIndex(0);
           // set1.setValues(yVals);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            set1 = new LineDataSet(yVals, "DataSet 1");
//
//            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            set1.setCubicIntensity(0.2f);
            //set1.setDrawFilled(true);
            set1.setDrawCircles(false);
            set1.setLineWidth(1.8f);
            //set1.setCircleRadius(4f);
            set1.setCircleColor(Color.DKGRAY);
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            set1.setColor(Color.DKGRAY);
            set1.setFillColor(Color.DKGRAY);
            set1.setFillAlpha(100);
            set1.setDrawHorizontalHighlightIndicator(false);


            LineData data = new LineData(labels, set1);
            set1.setColors(ColorTemplate.COLORFUL_COLORS); //
            set1.setDrawCubic(true);
            set1.setDrawFilled(false);

            mChart.setData(data);
            mChart.animateY(5000);
        }
    }
}
