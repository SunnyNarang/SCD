package com.exodia.numeric.paycheque;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sunny.pay.Checker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;



public class LoginActivity extends AppCompatActivity {

    LinearLayout ll;
    String username_str;
    String password_str;
    Button b;
    Button a;
    boolean loginpressed=false;
    boolean login_d= false;
    LinearLayout login_ll;
    EditText password;
    EditText username;
    ImageView iv;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor Editor;    
    
    @Override
    public void onBackPressed() {
        if(login_d){
            login_d=false;
            ll.animate().alpha(0.0f).setDuration(500);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms

                    a.animate().scaleX(1.0f).setDuration(500);
                    b.animate().scaleX(1.0f).setDuration(500);
                    a.setVisibility(View.VISIBLE);
                    b.setVisibility(View.VISIBLE);

                }
            }, 500);

        }
        else {
            super.onBackPressed();
        }}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window= LoginActivity.this.getWindow();
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        if(actionBar!=null)
            actionBar.hide();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#3DBDFF"));
        }






        mSettings = this.getSharedPreferences("settings", 0);
        Editor = mSettings.edit();

        if(mSettings.getString("remember_me", "").equalsIgnoreCase("1")){
            Intent i = new Intent(LoginActivity.this,Checker.class);
            i.putExtra("username",mSettings.getString("username", ""));
            i.putExtra("name",mSettings.getString("name", ""));
            i.putExtra("balance",mSettings.getString("balance", ""));
            i.putExtra("account",mSettings.getString("account", ""));
            i.putExtra("password",mSettings.getString("password",""));
            startActivity(i);
            LoginActivity.this.finish();
        }

        // object Making
        TextView tv = (TextView) findViewById(R.id.talk);

        iv = (ImageView) findViewById(R.id.main);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        a = (Button) findViewById(R.id.login);
        b = (Button) findViewById(R.id.signup);
        ll = (LinearLayout) findViewById(R.id.ll);
        login_ll = (LinearLayout) findViewById(R.id.login_ll);

        login_ll.setAlpha(0.0f);
        ll.setAlpha(0.0f);

        iv.setAlpha(0.0f);
        tv.setAlpha(0.0f);
        tv.animate()
                .translationYBy(0)
                .alpha(1.0f)
                .translationY(-450)
                .setDuration(2000);

        iv.animate()
                .translationYBy(0)
                .alpha(1.0f)
                .translationY(-450)
                .setDuration(2000);


    }
    public void login(View v){
        login_d = true;
        a.animate().scaleX(0.0f).setDuration(500);
        b.animate().scaleX(0.0f).setDuration(500);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                a.setVisibility(View.INVISIBLE);
                b.setVisibility(View.INVISIBLE);
                ll.animate().alpha(1.0f).setDuration(500);


            }
        }, 500);

    }
    public void back(View v){
        login_d=false;
        ll.animate().alpha(0.0f).setDuration(500);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms

                a.animate().scaleX(1.0f).setDuration(500);
                b.animate().scaleX(1.0f).setDuration(500);
                a.setVisibility(View.VISIBLE);
                b.setVisibility(View.VISIBLE);

            }
        }, 500);


    }

    public void signup(View v){

        // Intent i = new Intent(LoginActivity.this,HomeActivity.class);
      //  startActivity(i);

        Intent i = new Intent(LoginActivity.this,SignUp.class);
       // ActivityOptionsCompat options = ActivityOptionsCompat.
      //          makeSceneTransitionAnimation(this, (View)iv, "logo");
        startActivity(i);
    }

    public void loginuser(View v){
        if(loginpressed==false){
            loginpressed=true;
            username_str = username.getText().toString();

            password_str = password.getText().toString();

            // Toast.makeText(this, "Username : "+username_str+"\nPassword : "+password_str, Toast.LENGTH_SHORT).show();
            if(username_str.equalsIgnoreCase("")||password_str.equalsIgnoreCase("")){
                Toast.makeText(this, "Please fill all Fields", Toast.LENGTH_SHORT).show();
            }else {
                ReportLoader rl = new ReportLoader(LoginActivity.this);
                rl.execute();
            }}

    }

    public class ReportLoader extends AsyncTask<Void,Void,String> {

        Context context;

        ReportLoader(Context context)
        {
            this.context=context;
        }
        @Override
        protected String doInBackground(Void... params) {


            try{

                URL url =new URL("https://exodia-incredible100rav.cs50.io/android/paycheque/login.php");
                HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);

                OutputStream outputstream = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

                String post_data = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(username_str, "UTF-8") + "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password_str, "UTF-8");

                bufferedwriter.write(post_data);
                bufferedwriter.flush();
                bufferedwriter.close();

                InputStream inputstream= httpurlconnection.getInputStream();
                BufferedReader bufferedreader= new BufferedReader(new InputStreamReader(inputstream,"iso-8859-1"));
                String result="";
                String line="";
                while((line = bufferedreader.readLine())!=null){
                    result+=line;
                }
                bufferedreader.close();
                inputstream.close();
                httpurlconnection.disconnect();

                return result;

            }catch(MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return "";
        }


        @Override
        protected void onPreExecute() {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);

            ll.animate().alpha(0.0f).setDuration(1000);
            //a.animate().alpha(0.0f).setDuration(1000);
            //b.animate().alpha(0.0f).setDuration(1000);
            login_ll.animate().alpha(1.0f).setDuration(1000);

            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result ) {
            String username1="";
          //  Toast.makeText(LoginActivity.this, ""+result, Toast.LENGTH_SHORT).show();
            JSONObject obj = null;
            if(result.equalsIgnoreCase("")){login_ll.animate().alpha(0.0f).setDuration(1000);
                username.setText("");
                password.setText("");
                username.requestFocus();
                Toast.makeText(context, "Error Occured. Try Again", Toast.LENGTH_SHORT).show();
                LoginActivity.this.finish();
                startActivity(new Intent(LoginActivity.this,LoginActivity.class));
            }
            else if(result.equalsIgnoreCase("0")){

                Toast.makeText(context, "Username/Password Wrong.", Toast.LENGTH_SHORT).show();
                /*ll.animate().alpha(1.0f).setDuration(1000);
                //  a.animate().alpha(0.0f).setDuration(1000);
                // b.animate().alpha(0.0f).setDuration(1000);
                login_ll.animate().alpha(0.0f).setDuration(1000);
                username.setText("");
                password.setText("");
                username.requestFocus();
                */

                LoginActivity.this.finish();
                startActivity(new Intent(LoginActivity.this,LoginActivity.class));
            }
            else{login_ll.animate().alpha(0.0f).setDuration(1000);
                username.setText("");
                password.setText("");
                username.requestFocus();
                try {
                    JSONArray array = new JSONArray(result);

                    obj = array.getJSONObject(0);

                    username1 = obj.optString("acc");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Editor.putString("account", username1);
                Editor.putString("name",obj.optString("name"));
                Editor.putString("phone",obj.optString("phone"));
                Editor.putString("email",obj.optString("email"));
                Editor.putString("balance",obj.optString("balance"));
                Editor.putString("password",password_str);
                Editor.putString("remember_me", "1");
                Editor.apply();

                Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                i.putExtra("account",username1);
                i.putExtra("name",obj.optString("name"));
                i.putExtra("phone",obj.optString("phone"));
                i.putExtra("email",obj.optString("email"));
                i.putExtra("balance",obj.optString("balance"));
                startActivity(i);
                LoginActivity.this.finish();

            }
            // Toast.makeText(LoginActivity.this, ""+username1, Toast.LENGTH_SHORT).show();
        }
    }
}
