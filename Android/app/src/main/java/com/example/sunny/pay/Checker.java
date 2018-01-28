package com.example.sunny.pay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.exodia.numeric.paycheque.HomeActivity;
import com.exodia.numeric.paycheque.LoginActivity;
import com.exodia.numeric.paycheque.R;

public class Checker extends AppCompatActivity {
    TextView name_tv;
    private SharedPreferences mSettings;
    private SharedPreferences.Editor Editor;
    EditText pass_et;
    String password;

    String name,account_no,email,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checker);
        ActionBar ab = getSupportActionBar();
        ab.hide();
        mSettings = this.getSharedPreferences("settings", 0);
        Editor = mSettings.edit();
        name = getIntent().getStringExtra("name");
        password  = getIntent().getStringExtra("password");
        pass_et = (EditText) findViewById(R.id.pass);
        name_tv = (TextView) findViewById(R.id.name);
        name_tv.setText("Hello "+name);
    }

public void logout(View v){
    Editor.putString("remember_me", "0");
    Editor.apply();
    Intent i = new Intent(Checker.this,LoginActivity.class);
    startActivity(i);
    Checker.this.finish();
}

    public void check(View v){

        String password_user = pass_et.getText().toString();
        if(password_user.equals("")){
            Toast.makeText(Checker.this, "Enter Password", Toast.LENGTH_SHORT).show();
        }
        else if(password_user.equals(password)){
            Intent i = new Intent(Checker.this,HomeActivity.class);
            i.putExtra("username",mSettings.getString("username", ""));
            i.putExtra("name",mSettings.getString("name", ""));
            i.putExtra("balance",mSettings.getString("balance", ""));
            i.putExtra("account",mSettings.getString("account", ""));
            i.putExtra("password",mSettings.getString("password",""));
            startActivity(i);
            Checker.this.finish();
        }

        else{
            Toast.makeText(Checker.this, "Wrong Password", Toast.LENGTH_SHORT).show();
            pass_et.setText("");
        }



    }
}
