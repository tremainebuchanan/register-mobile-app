package com.tremainebuchanan.register.services;

import android.text.TextUtils;
import android.util.Log;

import com.tremainebuchanan.register.data.Session;
import com.tremainebuchanan.register.data.User;

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
    private static final String LOGIN_URL = "https://attend-app.herokuapp.com/login";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = Api.class.getSimpleName();

    public static String authUser(String user, OkHttpClient client){
        return post(user, client, LOGIN_URL);
    }

    private static String post(String json, OkHttpClient client, String url){
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        try{
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch (IOException e){
            Log.e(TAG, "Error encountered while attempting login");
        }
        return null;
    }

    private static String get(String resource, OkHttpClient client){
        String url = BASE_URL + resource;
        Request request = new Request.Builder().url(url).build();
        try{
            Response response = client.newCall(request).execute();
            return response.body().string();
        }catch(IOException e){
            Log.e(TAG, "Error encountered.");
        }
        return null;
    }

    public static ArrayList<Session> getSessions(OkHttpClient client){
        String response = get("sessions", client);
        return parseResponse(response);
    }

    private static ArrayList<Session> parseResponse(String response){
        ArrayList<Session> sessions = new ArrayList<>();
        if(TextUtils.isEmpty(response)){ return null; }

        try{
            JSONObject baseJSONResponse = new JSONObject(response);
            JSONArray sessionsArray = baseJSONResponse.getJSONArray("sessions");
            for (int i = 0; i < sessionsArray.length(); i++) {
                JSONObject session = sessionsArray.getJSONObject(i);
                String session_id = session.getString("_id");
                String session_name = session.getString("se_name");
                String session_date = session.getString("se_created");
                sessions.add(new Session(session_id, session_name, session_date, 25));
            }
            return sessions;
        }catch (JSONException e) {
            Log.e(TAG, "Problem parsing the student JSON results", e);
        }
        return null;
    }

}
