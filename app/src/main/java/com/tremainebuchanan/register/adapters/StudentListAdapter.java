package com.tremainebuchanan.register.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.data.Student;

import java.util.ArrayList;

/**
 * Created by captain_kirk on 10/22/16.
 */

public class StudentListAdapter extends ArrayAdapter<Student> {
    private final String TAG = StudentListAdapter.class.getSimpleName();
    private ArrayList<Student> students;
    public StudentListAdapter(Context context, ArrayList<Student> students){
        super(context, 0, students);
        this.students = students;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Student student = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_list_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView gender = (TextView) convertView.findViewById(R.id.gender);
        final TextView initial = (TextView) convertView.findViewById(R.id.initial);
        final CheckBox attendance_status = (CheckBox) convertView.findViewById(R.id.attendance_status);

        name.setText(student.getName());
        gender.setText(student.getGender());
        //initial.setText("P");
        attendance_status.setTag(position);
        initial.setTag(position);
        attendance_status.setChecked(students.get(position).isPresent());
        if (attendance_status.isChecked()) {
            initial.setText("P");
        }else{
            initial.setText("A");
        }

        attendance_status.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();
                students.get(getPosition).setPresent(buttonView.isChecked());
                if(!students.get(getPosition).isPresent()){
                    initial.setText("A");
                   // Toast.makeText(getContext(), "absent", Toast.LENGTH_LONG).show();
                }
            }
        });
        return convertView;
    }
}
