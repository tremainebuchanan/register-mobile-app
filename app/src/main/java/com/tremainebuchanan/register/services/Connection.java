package com.tremainebuchanan.register.services;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by captain_kirk on 10/6/16.
 */

public class Connection {

    public static boolean isConnected(Context context){
        ConnectivityManager cmgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cmgr.getActiveNetworkInfo();
        if(networkInfo != null && networkInfo.isConnected()) return true;
        return false;
    }
}
