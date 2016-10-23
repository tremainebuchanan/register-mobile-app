package com.tremainebuchanan.register.adapters;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.data.Student;

import java.util.ArrayList;

/**
 * Created by captain_kirk on 10/23/16.
 */

public class AbsentListAdapter extends ArrayAdapter<Student>{
    private ArrayList<Student> students;
    public AbsentListAdapter(Context context, ArrayList<Student> students){
        super(context, 0, students);
        this.students = students;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_absent_list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        name.setText(student.getName());

        return convertView;
    }
}
