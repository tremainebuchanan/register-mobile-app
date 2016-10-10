package com.tremainebuchanan.register.services;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.tremainebuchanan.register.data.Session;
import com.tremainebuchanan.register.data.Student;
import com.tremainebuchanan.register.data.User;
import com.tremainebuchanan.register.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by captain_kirk on 10/1/16.
 */

public class Api {
    private static final String BASE_URL = "https://attend-app.herokuapp.com/";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String PRESENT_ID = "57f41e579e5dc466137b18a4";
    private static final String ABSENT_ID = "57f41e6d9e5dc466137b18a5";
    private static final String TAG = Api.class.getSimpleName();

    public static String authUser(String user, OkHttpClient client){
        return post(user, client, "login");
    }

    private static String post(String json, OkHttpClient client, String url){
        RequestBody body = RequestBody.create(JSON, json);
        String abs_url = BASE_URL + url;
        Request request = new Request.Builder().url(abs_url).post(body).build();
        try{
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (IOException e){
            Log.e(TAG, "Error encountered while attempting login");
        }
        return null;
    }

    private static String get(String rel_url, OkHttpClient client){
        String url = BASE_URL + rel_url;
        Request request = new Request.Builder().url(url).build();
        try{
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch(IOException e){
            Log.e(TAG, "Error encountered.");
        }
        return null;
    }

    public static ArrayList<Session> getRegisters(OkHttpClient client, String user_id){
        String rel_url = "registers?re_assigned_to=" + user_id;
        String response = get(rel_url, client);
        return parseResponse(response);
    }

    public static ArrayList<Student> getStudents(OkHttpClient client, String id){
        String rel_url = "registers/" + id;
        String response = get(rel_url, client);
        return parseStudentResponse(response);
    }

    public static String markRegister(OkHttpClient client, String register_id, String json ){
        String rel_url = "students/" + register_id + "/attendance";
        String response = post(json, client, rel_url);
        return parseRegisterResponse(response);
        //return null;
    }

    private static String parseRegisterResponse(String response){
        try {
            JSONObject res = new JSONObject(response);
            if(res.getString("message").equals("success")){
                return "success";
            }
        }catch(JSONException e){
            Log.e(TAG, "Error in parsing attendance post response");
        }
        return null;
    }

    private static ArrayList<Student> parseStudentResponse(String response){
        ArrayList<Student> students = new ArrayList<>();
        if(TextUtils.isEmpty(response)){ return null; }

        try{
            JSONObject baseJSONResponse = new JSONObject(response);
            JSONArray studentsArray = baseJSONResponse.getJSONArray("students");
            for(int i = 0; i< studentsArray.length(); i++){
                JSONObject student = studentsArray.getJSONObject(i);
                String student_name = student.getString("name");
                String student_id = student.getString("_id");
                String gender = student.getString("st_gender");
                String contact = student.getString("st_contact");
                students.add(new Student(student_id, student_name, gender, true, contact));
            }
            return students;
        }catch (JSONException e) {
            Log.e(TAG, "Problem parsing the student JSON results", e);
        }
        return null;
    }

    private static ArrayList<Session> parseResponse(String response){
        ArrayList<Session> sessions = new ArrayList<>();
        if(TextUtils.isEmpty(response)){ return null; }

        try{
            JSONArray baseArray = new JSONArray(response);
            for(int i = 0; i < baseArray.length();  i++){
                JSONObject register = baseArray.getJSONObject(i);
                String session_id = register.getString("_id");
                JSONObject subject = register.getJSONObject("su_id");
                String session_name = subject.getString("su_title");
                String student_count = register.getString("count");
                String subject_id = subject.getString("_id");
                JSONArray students = register.getJSONArray("students");
                sessions.add(new Session(session_id, session_name, student_count, subject_id, students.toString()));
            }
            return sessions;
        }catch (JSONException e) {
            Log.e(TAG, "Problem parsing the student JSON results", e);
        }
        return null;
    }
    //TODO move both functions to Student class
    public static String getPresentId(){
        return PRESENT_ID;
    }

    public static String getAbsentId(){
        return ABSENT_ID;
    }


}
