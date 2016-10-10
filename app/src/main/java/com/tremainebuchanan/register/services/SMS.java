package com.tremainebuchanan.register.services;

import android.content.res.Resources;
import android.telephony.SmsManager;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.data.Student;
import java.util.ArrayList;

/**
 * Created by captain_kirk on 10/9/16.
 */

public class SMS {
    private static final String TAG = SMS.class.getSimpleName();
    public static void send(ArrayList<Student> students, String title){
        String message = "";
        int len = students.size();
        SmsManager manager = SmsManager.getDefault();
        for(int i=0;i<len;i++){
            message = students.get(i).getName() + " was absent from " + title + " today.";
//            message = students.get(i).getName() + " " + Resources.getSystem().getString(R.string.sms_start)
//                    + " " + title + " " + Resources.getSystem().getString(R.string.sms_end);
            manager.sendTextMessage("+18764477877", null, message, null, null);
        }
    }
}
