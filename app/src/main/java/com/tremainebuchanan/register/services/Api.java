package com.tremainebuchanan.register.services;

import android.util.Log;

import com.tremainebuchanan.register.data.User;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by captain_kirk on 10/1/16.
 */

public class Api {
    private static final String LOGIN_URL = "https://attend-app.herokuapp.com/login";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = Api.class.getSimpleName();


    public static String authUser(String user, OkHttpClient client){
        return makeHTTPCall(user, client, LOGIN_URL);
    }

    private static String makeHTTPCall(String json, OkHttpClient client, String url){
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

}
