package com.tremainebuchanan.register.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by captain_kirk on 10/29/16.
 */

public class SettingsPreference {

    private static SharedPreferences getSettingsPrefs(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
    /**
     * Determines if an SMS is sent upon individual's absence.
     * @param context
     * @return boolean
     */
    public static boolean sendSMS(Context context){
        return getSettingsPrefs(context).getBoolean("send_sms", false);
    }
}
