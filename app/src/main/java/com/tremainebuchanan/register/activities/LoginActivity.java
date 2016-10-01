package com.tremainebuchanan.register.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tremainebuchanan.register.R;
import com.tremainebuchanan.register.data.User;
import com.tremainebuchanan.register.services.Api;
import com.tremainebuchanan.register.utils.AppConfig;
import com.tremainebuchanan.register.utils.JSONUtil;
import com.tremainebuchanan.register.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    OkHttpClient client;
    EditText email;
    EditText password;
    Context context;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        if(SessionManager.isLoggedIn(context)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }else{
            client = new OkHttpClient();
            Button btn = (Button) findViewById(R.id.btnLogin);
            email = (EditText) findViewById(R.id.email);
            password = (EditText) findViewById(R.id.password);

            btn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    attemptLogin();
                }
            });
        }
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String json = JSONUtil.toJSON(user);
            return Api.authUser(json, client);
        }
        @Override
        protected void onPostExecute(String result) {
            processServerResponse(result);
        }
    }

    private boolean validateEmail(String email){
        return email.isEmpty();
    }
    private boolean validatePassword(String password){
        return password.isEmpty();
    }

   public void attemptLogin(){
       String user_email = email.getText().toString().trim();
       String user_password = password.getText().toString().trim();
       if (!validateEmail(user_email) && !validatePassword(user_password)) {
           user = new User(user_email, user_password);
           LoginTask loginTask = new LoginTask();
           loginTask.execute("");
       }else{
           Toast.makeText(getApplicationContext(), R.string.credentials_error, Toast.LENGTH_LONG).show();
       }
    }

    private void processServerResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("success").equals("true")) {
                JSONObject user = jsonObject.getJSONObject("user");
                SessionManager.setUser(context, user);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                Log.i(TAG, "User authenticated");
            }else{
                Toast.makeText(getApplicationContext(), R.string.login_attempt_failure ,
                        Toast.LENGTH_LONG).show();
            }
        }catch(JSONException e){
            Log.e(TAG, "Error in parsing json server response");
        }
    }


}
