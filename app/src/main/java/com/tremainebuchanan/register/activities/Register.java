package com.tremainebuchanan.register.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.adapters.SessionAdapter;
import com.tremainebuchanan.register.adapters.StudentAdapter;
import com.tremainebuchanan.register.data.Session;
import com.tremainebuchanan.register.data.Student;
import com.tremainebuchanan.register.services.Api;
import com.tremainebuchanan.register.utils.DividerItemDecorator;
import com.tremainebuchanan.register.utils.JSONUtil;
import com.tremainebuchanan.register.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class Register extends AppCompatActivity {
    OkHttpClient client;
    Context context;
    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private ArrayList<Student> studentList = new ArrayList<>();
    private StudentAdapter mAdapter;
    private static final String TAG = Register.class.getSimpleName();
    String re_id, su_id, title, students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        re_id = intent.getStringExtra("re_id");
        su_id = intent.getStringExtra("su_id");
        students = intent.getStringExtra("students");
        setTitle(title);

        spinner = (ProgressBar) findViewById(R.id.progressbar);
        spinner.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.student_list);

        mAdapter = new StudentAdapter(studentList);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecorator(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        client = new OkHttpClient();
        context = this;
        //new StudentTask().execute("");
        renderStudentList(students);

    }

    private class StudentTask extends AsyncTask<String, Void, ArrayList<Student>>{
        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Student> doInBackground(String... urls) {
            return Api.getStudents(client, re_id);
        }
        @Override
        protected void onPostExecute(ArrayList<Student> result) {
            spinner.setVisibility(View.GONE);
            updateUI(result);
        }
    }

    private class MarkTask extends AsyncTask<String, Void, String>{

        @Override
        protected void onPostExecute(String result) {
            showDialog();
        }

        @Override
        protected String doInBackground(String... params) {
            return Api.markRegister(client, re_id, params[0]);
        }
    }

    private void updateUI(ArrayList<Student> students){
        studentList = students;
        mAdapter = new StudentAdapter(students);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void renderStudentList(String students){
        try{
            ArrayList<Student> studentsList = new ArrayList<>();
            JSONArray studentsArray = new JSONArray(students);
            int len = studentsArray.length();
            for(int i=0; i < len; i++){
                JSONObject student = studentsArray.getJSONObject(i);
                String student_id = student.getString("_id");
                String student_name = student.getString("name");
                studentsList.add(new Student(student_id, student_name, "male", true));
            }
            setAdapter(studentsList);
        }catch(JSONException e){
            Log.e(TAG, "Error in converting students list to array");
        }
    }

    private void setAdapter(ArrayList<Student> students){
        studentList = students;
        mAdapter = new StudentAdapter(students);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void mark(View view){
        ArrayList<String> attendanceList = new ArrayList<>();
        int len = studentList.size();
        for(int i=0;i<len;i++){
            String student = JSONUtil.toJSON(studentList.get(i), re_id, SessionManager.getUserId(context), SessionManager.getOrgId(context), su_id);
            attendanceList.add(student);
        }
        new MarkTask().execute(attendanceList.toString());
    }
    //TODO convert this into a snack bar or toast
    public void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle("Marked");
        alertDialogBuilder
                .setMessage("Your register has been marked")
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                            Register.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
