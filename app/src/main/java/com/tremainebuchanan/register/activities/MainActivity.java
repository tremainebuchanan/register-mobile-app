package com.tremainebuchanan.register.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.adapters.SessionAdapter;
import com.tremainebuchanan.register.data.Session;
import com.tremainebuchanan.register.services.Api;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar) findViewById(R.id.progressbar);
        spinner.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mAdapter = new SessionAdapter(sessionList);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        client = new OkHttpClient();
        context = this;
        new SessionTask().execute("");
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
            updateUI(result);
        }
    }

    private void updateUI(ArrayList<Session> sessions){
        mAdapter = new SessionAdapter(sessions);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
