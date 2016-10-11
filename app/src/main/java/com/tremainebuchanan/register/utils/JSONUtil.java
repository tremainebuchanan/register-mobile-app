package com.tremainebuchanan.register.utils;

import android.util.Log;
import com.tremainebuchanan.register.data.Student;
import com.tremainebuchanan.register.data.User;
import com.tremainebuchanan.register.services.Api;
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

    public static String toJSON(Student student, String re_id, String user_id, String or_id, String su_id){
        String status = Api.getPresentId();
        if(!student.isPresent()) status = Api.getAbsentId();
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("st_id", student.getId());
            jsonObject.put("re_id", re_id);
            jsonObject.put("at_type_id", status);
            jsonObject.put("at_marked_by", user_id);
            jsonObject.put("or_id", or_id);
            jsonObject.put("su_id", su_id);
            return jsonObject.toString();
        }catch (JSONException e){
            Log.e(TAG, "Error in creating user json");
        }
        return null;
    }

}
