package com.tremainebuchanan.register.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.data.Student;

import java.util.List;

/**
 * Created by captain_kirk on 10/3/16.
 */

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.MyViewHolder>{
    private List<Student> studentList;
    private final String TAG = StudentAdapter.class.getSimpleName();
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name, gender, initial;
        CheckBox attendance_status;
        public final Context context;
        public MyViewHolder(View view) {
            super(view);
            context = view.getContext();
            name = (TextView) view.findViewById(R.id.name);
            gender = (TextView) view.findViewById(R.id.gender);
            initial = (TextView) view.findViewById(R.id.initial);
            attendance_status = (CheckBox) view.findViewById(R.id.attendance_status);
        }
    }

    public StudentAdapter(List<Student> studentList){
        this.studentList = studentList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Student student = studentList.get(position);
        holder.name.setText(student.getName());
        holder.gender.setText(student.getGender());
        holder.initial.setText("P");

        holder.attendance_status.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String message = "marked present";
                if(!holder.attendance_status.isChecked()){
                    message = "marked absent";
                    holder.initial.setText("A");
                    student.setPresent(false);
                }else{
                    holder.initial.setText("P");
                    student.setPresent(true);
                }
                Toast.makeText(view.getContext(), holder.name.getText().toString() + " " + message, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

}
