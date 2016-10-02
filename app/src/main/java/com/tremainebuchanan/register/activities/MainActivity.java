package com.tremainebuchanan.register.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.adapters.SessionAdapter;
import com.tremainebuchanan.register.data.Session;
import com.tremainebuchanan.register.services.Api;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;

public class MainActivity extends AppCompatActivity {
    private List<Session> sessionList = new ArrayList<>();
    private RecyclerView recyclerView;
    OkHttpClient client;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        client = new OkHttpClient();
        context = this;
        new SessionTask().execute("");
    }

    private class SessionTask extends AsyncTask<String, Void, ArrayList<Session>>{
        @Override
        protected ArrayList<Session> doInBackground(String... urls) {
            return Api.getSessions(client);
        }
        @Override
        protected void onPostExecute(ArrayList<Session> result) {
            updateUI(result);
        }
    }

    private void updateUI(ArrayList<Session> sessions){
        SessionAdapter sessionAdapter = new SessionAdapter(sessions);
        recyclerView.setAdapter(sessionAdapter);
        sessionAdapter.notifyDataSetChanged();
    }
}
