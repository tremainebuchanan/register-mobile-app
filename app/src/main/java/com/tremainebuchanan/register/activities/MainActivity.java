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
import com.tremainebuchanan.register.data.Session;
import com.tremainebuchanan.register.services.Api;
import com.tremainebuchanan.register.services.Connection;
import com.tremainebuchanan.register.utils.DividerItemDecorator;
import com.tremainebuchanan.register.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    OkHttpClient client;
    Context context;
    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private List<Session> sessionList = new ArrayList<>();
    private SessionAdapter mAdapter;
    private final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar) findViewById(R.id.progressbar);
        spinner.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new SessionAdapter(sessionList, new SessionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Session session) {
                showStudentList(session);
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addItemDecoration(new DividerItemDecorator(this));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

       showSessionList((ArrayList<Session>) sessionList);

        client = new OkHttpClient();
        context = this;
        if(Connection.isConnected(context)){
            new SessionTask().execute("");
        }else{
            Toast.makeText(this, "No connection", Toast.LENGTH_LONG).show();
        }

    }

    private class SessionTask extends AsyncTask<String, Void, ArrayList<Session>>{
        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Session> doInBackground(String... urls) {
            return Api.getSessions(client, SessionManager.getUserId(context));
        }
        @Override
        protected void onPostExecute(ArrayList<Session> result) {
            spinner.setVisibility(View.GONE);
            showSessionList(result);
        }
    }

    private void showSessionList(ArrayList<Session> sessions){
        mAdapter = new SessionAdapter(sessions, new SessionAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Session session) {
                showStudentList(session);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    private void showStudentList(Session session){
        Intent intent = new Intent(MainActivity.this, Register.class );
        intent.putExtra("students", session.getStudents());
        intent.putExtra("title", session.getSessionName());
        intent.putExtra("re_id", session.getSessionId());
        intent.putExtra("su_id", session.getSubjectId());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
