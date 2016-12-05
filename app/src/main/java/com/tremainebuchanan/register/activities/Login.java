package com.tremainebuchanan.register.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
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
import com.tremainebuchanan.register.services.Connection;
import com.tremainebuchanan.register.utils.JSONUtil;
import com.tremainebuchanan.register.utils.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;
import okhttp3.OkHttpClient;

public class Login extends AppCompatActivity {
    private static final String TAG = Login.class.getSimpleName();
    OkHttpClient client;
    EditText email;
    EditText password;
    Context context;
    User user;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;
        if(SessionManager.isLoggedIn(context)){
            Intent intent = new Intent(Login.this, Main.class);
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

        public LoginTask(Context context){
            dialog = new ProgressDialog(context);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(getResources().getString(R.string.user_auth_progress));
        }

        protected void onPreExecute() {
            dialog.show();
        }

        @Override
        protected String doInBackground(String... urls) {
            String json = JSONUtil.toJSON(user);
            return Api.authUser(json, client);
        }
        @Override
        protected void onPostExecute(String result) {
            if(dialog.isShowing()) dialog.dismiss();
            processServerResponse(result);
        }
    }

    private boolean validateEmail(String email){
        return email.isEmpty() && email.contains("@");
    }

    private boolean validatePassword(String password){
        return password.isEmpty();
    }

   public void attemptLogin(){
       String user_email = email.getText().toString().trim();
       String user_password = password.getText().toString().trim();
       if (!validateEmail(user_email) && !validatePassword(user_password)) {
           user = new User(user_email, user_password);
           if (Connection.isConnected(context)) {
               new LoginTask(context).execute("");
           }else{
               showDialog();
           }
       }else{
           Toast.makeText(getApplicationContext(), R.string.credentials_error, Toast.LENGTH_LONG).show();
       }
    }

    private void processServerResponse(String response){
        Log.d(TAG, response);
        try{
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("success").equals("true")) {
                JSONObject user = jsonObject.getJSONObject("user");
                SessionManager.setUser(context, user);
                Intent intent = new Intent(Login.this, Main.class);
                startActivity(intent);
                finish();
                Log.i(TAG, "User authenticated");
            }else{
                Toast.makeText(getApplicationContext(), R.string.login_attempt_failure ,
                        Toast.LENGTH_LONG).show();
            }
        }catch(JSONException e){
            Log.e(TAG, "Error in parsing json server response for attempting user login");
        }
    }

    public void showDialog(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);
        alertDialogBuilder.setTitle("No network connection");
        alertDialogBuilder
                .setMessage("No internet connection found.")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

}
