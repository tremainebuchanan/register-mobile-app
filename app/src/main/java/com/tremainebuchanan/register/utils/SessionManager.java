package com.tremainebuchanan.register.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by captain_kirk on 10/1/16.
 */

public class SessionManager {
    private static final String APP_PREFS = "APP_PREFS";
    private static final String USER_ID = "user_id";
    private static final String LOGGED_IN = "logged_in";
    private static final String ORG_ID = "org_id";
    private static final String TAG = SessionManager.class.getSimpleName();

    public static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }

    public static boolean isLoggedIn(Context context){
        return getPrefs(context).getBoolean(LOGGED_IN, false);
    }

    public static void setUser(Context context, JSONObject user){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean(LOGGED_IN, true);
        try{
            editor.putString(USER_ID, user.getString("_id"));
            editor.putString(ORG_ID, user.getString("or_id"));
            editor.apply();
        }catch(JSONException e){
            Log.e(TAG, "Error in setting user");
        }
        Log.i(TAG, "User id set.");
    }

    public static String getUserId(Context context){
        return getPrefs(context).getString(USER_ID, null);
    }

    public static String getOrgId(Context context){
        return getPrefs(context).getString(ORG_ID, null);
    }

    public static boolean removeUser(Context context){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.clear().apply();
        return true;
    }


}
