package com.tremainebuchanan.register.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class Main extends AppCompatActivity {
    OkHttpClient client;
    Context context;
    private ProgressBar spinner;
    private RecyclerView mRecyclerView;
    private List<Session> sessionList = new ArrayList<>();
    private SessionAdapter mAdapter;
    private final String TAG = Main.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (ProgressBar) findViewById(R.id.progressbar);
        spinner.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

       showSessionList((ArrayList<Session>) sessionList);

        client = new OkHttpClient();
        context = this;
        if(Connection.isConnected(context)){
            new GetRegistersTask().execute("");
        }else{
            Toast.makeText(this, "No connection", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class GetRegistersTask extends AsyncTask<String, Void, ArrayList<Session>>{
        @Override
        protected void onPreExecute() {
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        protected ArrayList<Session> doInBackground(String... urls) {
            return Api.getRegisters(client, SessionManager.getUserId(context));
        }
        @Override
        protected void onPostExecute(ArrayList<Session> result) {
            spinner.setVisibility(View.GONE);
            showSessionList(result);
        }
    }

    private void showSessionList(ArrayList<Session> sessions){
        mAdapter = new SessionAdapter(sessions);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
