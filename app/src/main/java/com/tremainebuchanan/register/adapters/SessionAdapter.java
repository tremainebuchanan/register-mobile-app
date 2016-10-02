package com.tremainebuchanan.register.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.data.Session;

import java.util.List;

/**
 * Created by captain_kirk on 10/1/16.
 */

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.MyViewHolder> {
    private List<Session> sessionList;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView numStudents, name, date;
        public MyViewHolder(View view) {
            super(view);
            numStudents = (TextView) view.findViewById(R.id.num_of_students);
            name = (TextView) view.findViewById(R.id.session_name);
            date = (TextView) view.findViewById(R.id.session_date);
        }
    }

    public SessionAdapter(List<Session> sessionList){
        this.sessionList = sessionList;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Session session = sessionList.get(position);
        holder.numStudents.setText(session.getNumberofStudents().toString());
        holder.name.setText(session.getSessionName());
        holder.date.setText(session.getCreated());
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

}
