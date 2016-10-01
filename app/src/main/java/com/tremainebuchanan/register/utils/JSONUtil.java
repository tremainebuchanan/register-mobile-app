package com.tremainebuchanan.register.utils;

import android.util.Log;

import com.tremainebuchanan.register.data.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by captain_kirk on 9/29/16.
 */
public class JSONUtil {
    private static final String TAG = JSONUtil.class.getSimpleName();
    public static String toJSON(User user){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", user.getEmail());
            jsonObject.put("password", user.getPassword());
            return jsonObject.toString();
        }catch (JSONException e){
            Log.e(TAG, "Error in creating user json");
        }
        return null;
    }
}
