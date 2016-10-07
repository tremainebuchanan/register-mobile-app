package com.tremainebuchanan.register.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.activities.Register;
import com.tremainebuchanan.register.data.Session;

import java.util.List;

/**
 * Created by captain_kirk on 10/1/16.
 */

public class SessionAdapter extends RecyclerView.Adapter<SessionAdapter.ViewHolder>{

    public interface OnItemClickListener{
         void onItemClick(Session session);
    }

    private List<Session> sessionList;
    private static final String TAG = SessionAdapter.class.getSimpleName();

    OnItemClickListener listener;

    public SessionAdapter(List<Session> sessionList, OnItemClickListener listener){
        this.listener = listener;
        this.sessionList = sessionList;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.session_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        final Session session = sessionList.get(position);
//        holder.name.setText(session.getSessionName());
//        holder.count.setText(session.getStudentCount());
        holder.bind(sessionList.get(position), listener);
//        holder..setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view) {
//                Log.i(TAG, session.getStudents());
//                String activity_title = holder.name.getText().toString();
//                Intent intent = new Intent(view.getContext(), Register.class );
//                intent.putExtra("students", session.getStudents());
//                intent.putExtra("title", activity_title);
//                intent.putExtra("re_id", session.getSessionId());
//                intent.putExtra("su_id", session.getSubjectId());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                view.getContext().startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return sessionList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, count;
        public final Context context;

        public ViewHolder(final View view) {
            super(view);
            context = view.getContext();
            count = (TextView) view.findViewById(R.id.count);
            name = (TextView) view.findViewById(R.id.session_name);
        }

        public void bind(final Session session, final OnItemClickListener listener) {
            name.setText(session.getSessionName());
            count.setText(session.getStudentCount());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(session);
                }
            });
        }
    }

}
