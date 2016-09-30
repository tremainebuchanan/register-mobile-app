package com.tremainebuchanan.register;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tremainebuchanan.register.data.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = LoginActivity.class.getSimpleName();
    OkHttpClient client;
    String user_email = "";
    String user_password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        client = new OkHttpClient();
        Button btn = (Button) findViewById(R.id.btnLogin);
        final EditText email = (EditText) findViewById(R.id.email);
        final EditText password = (EditText) findViewById(R.id.password);

        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                user_email = email.getText().toString().trim();
                user_password = password.getText().toString().trim();
                if (!validateEmail(user_email) && !validatePassword(user_password)) {
                    LoginTask loginTask = new LoginTask();
                    loginTask.execute("");
                }else{
                    Toast.makeText(getApplicationContext(), R.string.credentials_error, Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private class LoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String response = attemptLogin();
            return response;
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

   public String attemptLogin(){
        User user = new User(user_email, user_password);
        String json = JSONUtil.toJSON(user);
        if(json != null){
            String url = AppConfig.LOGIN_URL;
            RequestBody body = RequestBody.create(JSON, json);
            Request request = new Request.Builder().url(url).post(body).build();
            try{
                Response response = client.newCall(request).execute();
                return response.body().string();
            }catch (IOException e){
                Log.e(TAG, "Error encountered while attempting login");
            }
        }
        return null;
    }

    private void processServerResponse(String response){
        try{
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("success").equals("true")) {
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
