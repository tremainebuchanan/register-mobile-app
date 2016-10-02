package com.tremainebuchanan.register.utils;

/**
 * Created by captain_kirk on 10/1/16.
 */

import android.text.TextUtils;
import android.util.Log;

import com.tremainebuchanan.register.data.Session;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by captain_kirk on 9/18/16.
 */
public final class Utils {
    public static final String LOG_TAG = Utils.class.getSimpleName();

    public static ArrayList<Session> parseResponse(String response){
        ArrayList<Session> sessions = new ArrayList<>();

        if(TextUtils.isEmpty(response)){ return null; }

        try{
            JSONObject baseJSONResponse = new JSONObject(response);
            JSONArray sessionsArray = baseJSONResponse.getJSONArray("sessions");
            for (int i = 0; i < sessionsArray.length(); i++) {
                JSONObject session = sessionsArray.getJSONObject(i);
                String session_id = session.getString("_id");
                String session_name = session.getString("se_name");
                //JSONObject session_type_name = session.getJSONObject("se_type_id");
                JSONArray students = session.getJSONArray("st_students");
                //String session_name = session_type_name.getString("se_type_name");
                String session_date = session.getString("se_created");
                sessions.add(new Session(session_id, session_name, session_date, students.length()));
            }
            return sessions;
        }catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the student JSON results", e);
        }

        return null;
    }
}
