package com.exodia.numeric.paycheque;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

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


public class SignUp extends AppCompatActivity {

    String name="",acc="",email="",phone="",bal="",password="";
    RelativeLayout register_ll;
    LinearLayout signup_ll;
    EditText name_et,acc_et,email_et,phone_et,bal_et,password_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ActionBar ab = getSupportActionBar();
        if(ab!=null)
            ab.hide();
        name_et = (EditText) findViewById(R.id.name);
        acc_et = (EditText) findViewById(R.id.acc);
        bal_et = (EditText) findViewById(R.id.bal);
        email_et = (EditText) findViewById(R.id.email);
        phone_et = (EditText) findViewById(R.id.phone);
        password_et = (EditText) findViewById(R.id.password);
        register_ll = (RelativeLayout) findViewById(R.id.register_ll);
        register_ll.setVisibility(View.INVISIBLE);
        register_ll.setAlpha(0f);
    }






    /**
     * helper to retrieve the path of an image URI
     */

    public void signupprofile(View v){


        name = name_et.getText().toString();
        email = email_et.getText().toString();
        phone = phone_et.getText().toString();
        bal=bal_et.getText().toString();
        acc=acc_et.getText().toString();
       password=password_et.getText().toString();
        //Toast.makeText(this, ""+f_name+"\n"+l_name+"\n"+username+"\n"+email+"\n"+password+"\n"+age+"\n"+phone+"\n", Toast.LENGTH_SHORT).show();
        if(!password.equalsIgnoreCase("")&&!name.equalsIgnoreCase("")&&!email.equalsIgnoreCase("")&&!phone.equalsIgnoreCase("")&&!bal.equalsIgnoreCase("")&&!acc.equalsIgnoreCase("")) {
            ReportLoader rl = new ReportLoader(SignUp.this);
            rl.execute();
        }
        else{
            Toast.makeText(this, "Please Fill All Feilds", Toast.LENGTH_SHORT).show();
        }
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

                URL url =new URL("https://exodia-incredible100rav.cs50.io/android/paycheque/signup.php");
                HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);

                OutputStream outputstream = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

                String post_data =URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8")+ "&"
                        + URLEncoder.encode("phone", "UTF-8") + "=" + URLEncoder.encode(phone, "UTF-8")+ "&"
                        + URLEncoder.encode("acc", "UTF-8") + "=" + URLEncoder.encode(acc, "UTF-8")+ "&"
                        + URLEncoder.encode("bal", "UTF-8") + "=" + URLEncoder.encode(bal, "UTF-8")+ "&"
                        + URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8")+ "&"
                        + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8");

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

            register_ll.setVisibility(View.VISIBLE);
            register_ll.animate().alpha(1.0f).setDuration(500);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result ) {
            //Toast.makeText(SignUp.this, ""+result, Toast.LENGTH_SHORT).show();
            register_ll.animate().alpha(0.0f).setDuration(500);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    // Do something after 5s = 5000ms
                    register_ll.setVisibility(View.INVISIBLE);
                }
            }, 500);


            if(result.equalsIgnoreCase("0")){
                Toast.makeText(context, "Sorry Username Already Registered. Try Another", Toast.LENGTH_SHORT).show();

            }
            else if(result.equalsIgnoreCase("1")){
                Toast.makeText(context, "Registered Successfully.", Toast.LENGTH_LONG).show();
                Intent i = new Intent(SignUp.this,LoginActivity.class);
                startActivity(i);
                SignUp.this.finish();
            }
            else{
                Toast.makeText(context, "Sorry Error Occured. Try Again", Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(SignUp.this, ""+result, Toast.LENGTH_SHORT).show();
        }
    }


    public void backtosplash(View v){
        SignUp.this.finish();
    }
}
