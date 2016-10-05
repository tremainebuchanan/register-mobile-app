package com.tremainebuchanan.register.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.activities.MainActivity;
import com.tremainebuchanan.register.activities.Register;
import com.tremainebuchanan.register.data.Session;

import java.util.List;

/**
 * Created by captain_kirk on 10/1/16.
 */

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder>{
    private List<Session> sessionList;
    private static final String TAG = "SessionAdapter";
    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView numStudents, name, date, status;
        Button btn;
        CardView cv;
        public final Context context;
        public ViewHolder(final View view) {
            super(view);
            context = view.getContext();
            //numStudents = (TextView) view.findViewById(R.id.num_of_students);
            name = (TextView) view.findViewById(R.id.session_name);
            //date = (TextView) view.findViewById(R.id.session_date);
            status = (TextView) view.findViewById(R.id.status);
            cv = (CardView) view.findViewById(R.id.card_view);
        }
    }

    public SessionAdapter(List<Session> sessionList){
        this.sessionList = sessionList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Session session = sessionList.get(position);
        holder.name.setText(session.getSessionName());
        holder.status.setText(session.getStatus());
        holder.cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String activity_title = holder.name.getText().toString();
                Intent intent = new Intent(view.getContext(), Register.class );
                intent.putExtra("title", activity_title);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                view.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }

}
