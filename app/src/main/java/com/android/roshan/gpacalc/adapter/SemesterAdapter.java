package com.android.roshan.gpacalc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.roshan.gpacalc.R;
import com.android.roshan.gpacalc.models.Semester;
import com.android.roshan.gpacalc.models.Subject;

import java.util.ArrayList;

/**
 * Created by amilah on 09-Mar-17.
 */

public class SemesterAdapter extends ArrayAdapter<Subject> {

    ArrayList<Subject> subjects;

    public SemesterAdapter(Context context, ArrayList<Subject> subjects) {
        super(context,0, subjects);
        this.subjects = subjects;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Subject subject = getItem(position);
        if(convertView==null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.subject_list_item,parent,false);
        }

        TextView sub_code = (TextView)convertView.findViewById(R.id.sub_code);
        TextView sub_name = (TextView)convertView.findViewById(R.id.sub_name);
        TextView sub_credit = (TextView)convertView.findViewById(R.id.sub_credit);
        TextView sub_result = (TextView)convertView.findViewById(R.id.sub_result);
        Log.i("sub",""+subject.getSub_code()+"--"+ subject.getSub_name() +""+subject.getSub_credit()+""+subject.getSub_result());
        sub_code.setText(""+subject.getSub_code());
        sub_name.setText(""+subject.getSub_name());
        sub_credit.setText(""+subject.getSub_credit());
        sub_result.setText(""+subject.getSub_result());
        return convertView;
    }
}
