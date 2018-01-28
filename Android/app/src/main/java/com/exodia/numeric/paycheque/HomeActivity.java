package com.exodia.numeric.paycheque;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.exodia.numeric.paycheque.model.Cheque;

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
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    private String name,account_no,phone,email,balance;
    private SharedPreferences mSettings;
    ListView ll;
    TextView tv_acc;
    ProgressBar progress;
    CustomAdapter3 adapter;
    boolean flag = false;
    ArrayList<Cheque> list = new ArrayList<>();
    private SharedPreferences.Editor Editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSettings = this.getSharedPreferences("settings", 0);
        Editor = mSettings.edit();
        Window window= HomeActivity.this.getWindow();
        setContentView(R.layout.activity_home);

        ActionBar actionBar = getSupportActionBar(); // or getActionBar();
        if(actionBar!=null)
            actionBar.hide();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(Color.parseColor("#3DBDFF"));
        }
        ll = (ListView) findViewById(R.id.history_list);
        adapter = new CustomAdapter3(HomeActivity.this,list);
        ll.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        progress = (ProgressBar) findViewById(R.id.progress);
        progress.setVisibility(View.INVISIBLE);
        FilesLoader loader = new FilesLoader(this);
        loader.execute();

        name = getIntent().getStringExtra("name");
        balance  = "";//getIntent().getStringExtra("balance");
        String initials=name.charAt(0)+"";
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);

            if (c == ' ') {
                initials+=name.charAt(i + 1);
            }

        }
        account_no = getIntent().getStringExtra("account");
        email = getIntent().getStringExtra("email");
        phone = getIntent().getStringExtra("phone");
       // Toast.makeText(HomeActivity.this, ""+account_no, Toast.LENGTH_SHORT).show();
        ImageView dp = (ImageView) findViewById(R.id.imageView);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(initials.toUpperCase(), Color.parseColor("#3DBDFF"));
        dp.setImageDrawable(drawable);
        TextView tv_name = (TextView) findViewById(R.id.tv_username);

        tv_acc = (TextView) findViewById(R.id.tv_account_no);
        tv_acc.setText("Acc: "+account_no+" | Balance: "+balance);
        tv_name.setText(name);

    }
    public void pay(View v){
        Intent i = new Intent(this,QRGeneratorActivity.class);
        i.putExtra("name",name);
        i.putExtra("acc",account_no);
        i.putExtra("phone",phone);
        startActivity(i);
    }
    public void scan(View v){
        Intent i = new Intent(this,QrScannerActivity.class);
        startActivity(i);
    }
    public void logout(View v){
       Editor.putString("remember_me", "0");
       Editor.apply();
        Intent i = new Intent(HomeActivity.this,LoginActivity.class);
        startActivity(i);
        HomeActivity.this.finish();
    }

    class CustomAdapter3 extends ArrayAdapter<Cheque> {
        Context c;

        public CustomAdapter3(Context context, ArrayList<Cheque> arrayList) {
            super(context, R.layout.files_card, arrayList);
            this.c = context;
        }


        @Override
        public View getView(int pos, View convertView, ViewGroup parent) {

            LayoutInflater li = (LayoutInflater) c.getSystemService(c.LAYOUT_INFLATER_SERVICE);
            convertView = li.inflate(R.layout.files_card, parent, false);

            Cheque cheque = getItem(pos);

            TextView file_name = (TextView) convertView.findViewById(R.id.files_title);

            TextView file_desc = (TextView) convertView.findViewById(R.id.files_desc);

            TextView acount_no_tv = (TextView) convertView.findViewById(R.id.account);

            acount_no_tv.setText("( "+cheque.getIssued_to()+" )");
            file_name.setText(cheque.getName());
            file_desc.setText("Amount: "+cheque.getAmount());

            return convertView;

        }
    }

    public class FilesLoader extends AsyncTask<Void,Void,String> {

        Context context;
        FilesLoader(Context context)
        {
            this.context=context;
        }


        @Override
        protected String doInBackground(Void... params) {

            String login_url= "https://exodia-incredible100rav.c9users.io/android/paycheque/history.php";

            try{

                URL url =new URL(login_url);
                HttpURLConnection httpurlconnection= (HttpURLConnection) url.openConnection();
                httpurlconnection.setRequestMethod("POST");
                httpurlconnection.setDoOutput(true);
                httpurlconnection.setDoInput(true);

                OutputStream outputstream = httpurlconnection.getOutputStream();
                BufferedWriter bufferedwriter = new BufferedWriter(new OutputStreamWriter(outputstream, "UTF-8"));

                String post_data = URLEncoder.encode("acc", "UTF-8") + "=" + URLEncoder.encode(account_no, "UTF-8");

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


            return "null ghjgj";

        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progress.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String result ){
            //progress.setVisibility(View.INVISIBLE);

           // Toast.makeText(HomeActivity.this, ""+result, Toast.LENGTH_SHORT).show();
            if(result.contains(",")){
                String s[] = result.split("``%f%``");

                //if flag = true than it is running first time
                if(!balance.equals(s[0]) || balance.equals("")) {
                    //set amount data
                    balance = s[0];
                    tv_acc.setText("Acc: " + account_no + " | Balance: " + s[0]);
                    //set listview history data
                    String json = s[1];
                    //Toast.makeText(context, ""+s[1], Toast.LENGTH_SHORT).show();
                    JSONArray root = null;
                    list.clear();
                    try {
                        root = new JSONArray(json);

                        String debited ="";
                        for (int i = 0; i < root.length(); i++) {
                            JSONObject jsonObject = root.optJSONObject(i);

                            String name_from = jsonObject.optString("name_from");
                            String amount = jsonObject.optString("amount");
                            String acc_from = jsonObject.optString("acc_from");
                            //String acc_from=jsonObject.optString("acc_from");

                            Cheque cheque = new Cheque(null,null, name_from, amount, acc_from,null,null);
                            list.add(cheque);
                            debited = amount;
                        }
                        //  Toast.makeText(HomeActivity.this, ""+list.size(), Toast.LENGTH_SHORT).show();

                        adapter.notifyDataSetChanged();
                       /* Notification.Builder not = new Notification.Builder(HomeActivity.this)
                                .setContentTitle("Pay Cheque")
                                .setContentText("your Account has been Debited Rs."+debited)
                                .setSmallIcon(R.drawable.pay_logo);

                        NotificationManager nm = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
                        nm.notify(1, not.build());*/
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if(!HomeActivity.this.isDestroyed()){
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        new FilesLoader(HomeActivity.this).execute();
                    }
                }, 2000);

            }}
            flag = false;

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();

        }


    }

}

