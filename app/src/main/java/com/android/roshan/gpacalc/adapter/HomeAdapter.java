package com.android.roshan.gpacalc.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.models.Semester;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amilah on 09-Mar-17.
 */

public class HomeAdapter extends ArrayAdapter<Semester> {

    ArrayList<Semester> semesters;

    public HomeAdapter(Context context, ArrayList<Semester> semesters) {
        super(context,0, semesters);
        this.semesters = semesters;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Semester s = getItem(position);
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.semester_list_item,parent,false);
        }
        TextView sem_id = (TextView)convertView.findViewById(R.id.sem_no);
        TextView sem_gpa = (TextView)convertView.findViewById(R.id.sem_gpa);
        TextView sem_credit = (TextView)convertView.findViewById(R.id.sem_credit);
        if(s.getSgpa()<=1.50){
            sem_id.setTextColor(Color.RED);
            sem_gpa.setTextColor(Color.RED);
            sem_credit.setTextColor(Color.RED);
        }
        else if(s.getSgpa()>1.50 && s.getSgpa()<3.00){
            sem_id.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
            sem_gpa.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
            sem_credit.setTextColor(ContextCompat.getColor(getContext(),R.color.colorPrimaryDark));
        }
        else if(s.getSgpa()>3.00 && s.getSgpa()<3.70){
            sem_id.setTextColor(ContextCompat.getColor(getContext(),R.color.color_app_name));
            sem_gpa.setTextColor(ContextCompat.getColor(getContext(),R.color.color_app_name));
            sem_credit.setTextColor(ContextCompat.getColor(getContext(),R.color.color_app_name));
        }
        else {
            sem_id.setTextColor(ContextCompat.getColor(getContext(),R.color.safe));
            sem_gpa.setTextColor(ContextCompat.getColor(getContext(),R.color.safe));
            sem_credit.setTextColor(ContextCompat.getColor(getContext(),R.color.safe));
        }


        sem_id.setText(""+s.getId());
        sem_gpa.setText(""+s.getSgpa());
        sem_credit.setText(""+s.getScredit());
        return convertView;
    }
}
