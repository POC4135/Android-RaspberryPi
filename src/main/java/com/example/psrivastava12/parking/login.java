package com.example.psrivastava12.parking;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import  com.example.psrivastava12.parking.Park;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;


public class login extends Activity
{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        Button login;
        final TextView us,p;

        login = findViewById(R.id.login);
        us = findViewById(R.id.userid);
        p = findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Scan.class);

                if((us.getText().toString().compareTo("admin")==0)&&(p.getText().toString().compareTo("admin")==0)){
                    startActivity(i);
                }
                else
                {
                    us.setText("invalid");
                }

            }
        });

    }




}


