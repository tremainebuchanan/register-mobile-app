package com.tremainebuchanan.register.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.tremainebuchanan.register.utils.SessionManager;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        setTitle(title);

        spinner = (ProgressBar) findViewById(R.id.progressbar);
        spinner.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.student_list);

        mAdapter = new StudentAdapter(studentList);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        client = new OkHttpClient();
        context = this;
        new StudentTask().execute("");

    }

    private class StudentTask extends AsyncTask<String, Void, ArrayList<Student>>{
        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Student> doInBackground(String... urls) {
            return Api.getStudents(client, SessionManager.getUserId(context));
        }
        @Override
        protected void onPostExecute(ArrayList<Student> result) {
            spinner.setVisibility(View.GONE);
            updateUI(result);
        }
    }

    private void updateUI(ArrayList<Student> students){
        studentList = students;
        mAdapter = new StudentAdapter(students);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    public void mark(View view){
        int len = studentList.size();
        //Log.i(TAG, "" + len);
        for(int i=0;i<len;i++){
            Log.i(TAG, ""+studentList.get(i).isPresent());
        }
    }
}
