package com.tremainebuchanan.register.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.activities.Main;
import com.tremainebuchanan.register.activities.Register;
import com.tremainebuchanan.register.data.Session;

import java.util.List;

/**
 * Created by captain_kirk on 10/8/16.
 */

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder>{
    private List<Session> sessionList;
    private final String TAG = SessionAdapter.class.getSimpleName();

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, count, period;
        CardView card;
        public final Context context;
        public ViewHolder(View view) {
            super(view);
            context = view.getContext();
            count = (TextView) view.findViewById(R.id.count);
            name = (TextView) view.findViewById(R.id.session_name);
            period = (TextView) view.findViewById(R.id.period);
            card = (CardView) view.findViewById(R.id.session_card);
        }
    }

    public SessionAdapter(List<Session> sessionList){
        this.sessionList = sessionList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Session session = sessionList.get(position);
        holder.name.setText(session.getSessionName());
        holder.count.setText(session.getStudentCount());
        holder.period.setText(session.getPeriod());
        holder.card.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), Register.class );
                intent.putExtra("students", session.getStudents());
                intent.putExtra("title", session.getSessionName());
                intent.putExtra("re_id", session.getSessionId());
                intent.putExtra("su_id", session.getSubjectId());
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
