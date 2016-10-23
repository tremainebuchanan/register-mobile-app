package com.tremainebuchanan.register.activities;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.adapters.AbsentListAdapter;
import com.tremainebuchanan.register.adapters.StudentListAdapter;
import com.tremainebuchanan.register.data.Student;
import com.tremainebuchanan.register.services.Api;
import com.tremainebuchanan.register.services.SMS;
import com.tremainebuchanan.register.utils.JSONUtil;
import com.tremainebuchanan.register.utils.SessionManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import okhttp3.OkHttpClient;

public class Register extends AppCompatActivity {
    OkHttpClient client;
    Context context;
    private ProgressBar spinner;
    private static final String TAG = Register.class.getSimpleName();
    private ListView listView;
    private StudentListAdapter adapter;
    private ArrayList<Student> studentList = new ArrayList<>();
    String re_id, su_id, title, students;
    ProgressDialog dialog;
    ArrayList<Student> absentList;
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
        listView = (ListView) findViewById(R.id.student_list);
        adapter = new StudentListAdapter(this, studentList);
        client = new OkHttpClient();
        context = this;
        populateStudents(students);
        listView.setAdapter(adapter);
    }

    private class MarkTask extends AsyncTask<String, Void, String>{

        public MarkTask(Context context){
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getResources().getString(R.string.marking_progress));
        }

        @Override
        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected void onPostExecute(String result) {
            if(result.equals("success")){
                //SMS.send(absentList, title);
                if(dialog.isShowing()) dialog.dismiss();
               showDialog("Marked", "Your register has been marked");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            return Api.markRegister(client, re_id, params[0]);
        }
    }

    private void populateStudents(String students){
        try{
            ArrayList<Student> studentsList = new ArrayList<>();
            JSONArray studentsArray = new JSONArray(students);
            int len = studentsArray.length();
            for(int i=0; i < len; i++){
                JSONObject student = studentsArray.getJSONObject(i);
                String student_id = student.getString("_id");
                String student_name = student.getString("name");
                String contact = student.getString("st_contact");
                String gender = student.getString("st_gender");
                studentsList.add(new Student(student_id, student_name, gender, true, contact));
            }
            setAdapter(studentsList);
        }catch(JSONException e){
            Log.e(TAG, "Error in converting students list to array");
        }
    }
    /**
     * Set a listview adapter.
     * @param students - List of students
     */
    private void setAdapter(ArrayList<Student> students){
        studentList = students;
        adapter = new StudentListAdapter(this, students);
        listView.setAdapter(adapter);
    }
    /**
     * Prepares the attendance list
     * @param view
     */
    public void prepareAttendanceList(View view){
        ArrayList<String> attendanceList = new ArrayList<>();
        absentList = new ArrayList<>();
        int len = studentList.size();
        for(int i=0;i<len;i++){
            String student = JSONUtil.toJSON(studentList.get(i), re_id, SessionManager.getUserId(context), SessionManager.getOrgId(context), su_id);
            attendanceList.add(student);
            if(!studentList.get(i).isPresent()){
                absentList.add(studentList.get(i));
            }
        }
        confirmDialog(absentList, attendanceList.toString());
    }
    /**
     * Generic function to display a dialog.
     * @param title - The title of the dialog.
     * @param message - The message to the user.
     */
    //TODO convert this into a snack bar or toast
    public void showDialog(String title, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(title);
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                            Register.this.finish();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    /**
     * Shows confirmation dialog with list of absentees.
     * @param absentees - List of absent students
     * @param attendance - List of attendance record for all students
     */
    public void confirmDialog(final ArrayList<Student> absentees, final String attendance){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        AbsentListAdapter adapter = new AbsentListAdapter(this, absentees);
        LayoutInflater inflater = getLayoutInflater();
        View convertView = inflater.inflate(R.layout.student_absent_list, null);
        alertDialogBuilder.setView(convertView);
        ListView listView = (ListView) convertView.findViewById(R.id.absentees);
        listView.setAdapter(adapter);
        listView.setClickable(false);
        alertDialogBuilder
                .setTitle("Absent List")
                .setPositiveButton("Mark Register",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();
                        new MarkTask(context).execute(attendance);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
