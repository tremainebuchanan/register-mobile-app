package com.tremainebuchanan.register.services;

import android.telephony.SmsManager;
import com.tremainebuchanan.register.data.Student;
import java.util.ArrayList;

/**
 * Created by captain_kirk on 10/9/16.
 */

public class SMS {
    public static void send(ArrayList<Student> students, String title){
        String message = "";
        int len = students.size();
        SmsManager manager = SmsManager.getDefault();
        for(int i=0;i<len;i++){
            message = students.get(i).getName() + " was absent from Track & Field " + title +
                    " today at XLCR High. You will be billed for this AUTOMATED MESSAGE.";
            if(!students.get(i).getContact().isEmpty()){
                String contact = "+1876" + students.get(i).getContact();
                manager.sendTextMessage(contact, null, message, null, null);
            }

        }
    }
}
