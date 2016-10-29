package com.tremainebuchanan.register.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

public class SessionManager {
    private static final String APP_PREFS = "APP_PREFS";
    private static final String USER_ID = "user_id";
    private static final String LOGGED_IN = "logged_in";
    private static final String ORG_ID = "org_id";
    private static final String ORG_NAME = "org_name";
    private static final String TAG = SessionManager.class.getSimpleName();

    private static SharedPreferences getPrefs(Context context){
        return context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE);
    }
    /**
     * Determines if a user is currently logged in/
     * @param context
     * @return
     */
    public static boolean isLoggedIn(Context context){
        return getPrefs(context).getBoolean(LOGGED_IN, false);
    }

    /**
     * Sets a authenticated user to the application's preference.
     * @param context
     * @param user
     */
    public static void setUser(Context context, JSONObject user){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean(LOGGED_IN, true);
        try{
            JSONObject organization = user.getJSONObject("or_id");
            editor.putString(USER_ID, user.getString("_id"));
            editor.putString(ORG_ID, organization.getString("_id"));
            editor.putString(ORG_NAME, organization.getString("or_name"));
            editor.apply();
        }catch(JSONException e){
            Log.e(TAG, "Error in setting user");
        }
        Log.i(TAG, "User id set.");
    }

    /**
     * Gets the id of the logged in user.
     * @param context
     * @return  id - User's id.
     */
    public static String getUserId(Context context){
        return getPrefs(context).getString(USER_ID, null);
    }
    /**
     *  Gets the user's organization id.
     * @param context
     * @return id - Organization's id.
     */
    public static String getOrgId(Context context){
        return getPrefs(context).getString(ORG_ID, null);
    }
    /**
     * Gets the user's organization name.
     * @param context
     * @return
     */
    public static String getOrgName(Context context){
        SharedPreferences sharedPreferences = getPrefs(context);
        return sharedPreferences.getString(ORG_NAME, null);
    }
    /**
     *  Logs out the user.
     * @param context
     * @return true
     */
    public static boolean removeUser(Context context){
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.clear().apply();
        return true;
    }
}
