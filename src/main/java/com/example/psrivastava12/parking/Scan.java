package com.example.psrivastava12.parking;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import  com.example.psrivastava12.parking.Park;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;




public class Scan extends Activity
{

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan);


         Button scan,profile;

        scan = findViewById(R.id.Scan);
        profile = findViewById(R.id.Profile);
        scan.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                int SDK_INT = android.os.Build.VERSION.SDK_INT;
                if (SDK_INT > 8)
                {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                            .permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    //your codes here

                }

                Intent i = new Intent(getApplicationContext(), Park.class);

                URL url;

                try {
                    // get URL content
                    url = new URL("http://192.168.43.237:8080/");
                    URLConnection conn = url.openConnection();

                    // open the stream and put it into BufferedReader
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                    String inputLine;
                    Log.d("a","-----------------------------------------------------------------------------1");
                    //save to this filename
                    //String fileName = "E:/javaprograms/aaaaa.html";
                    //File file = new File(fileName);

                    //	if (!file.exists()) {
                    //		file.createNewFile();
                    //	}

                    //use FileWriter to write file
                    //FileWriter fw = new FileWriter(file.getAbsoluteFile());
                    //BufferedWriter bw = new BufferedWriter(fw);

                    while ((inputLine = br.readLine()) != null) {
                        //bw.write(inputLine);
                        System.out.println(inputLine);
                        Log.d("a",inputLine);
                        Log.d("a","-----------------------------------------------------------------------------2");
                        Log.d("a","-----------------------------------------------------------------------------3");
                        TextView tes = findViewById(R.id.fff);
                        //inputLine = "l"+inputLine+"1";
                        //tes.setText(inputLine);
                        //String f = tes.getText().toString();
                        i.putExtra("a", "scan");
                        i.putExtra("b", inputLine);
                        startActivity(i);
                    }

                    //bw.close();
                    br.close();

                    System.out.println("Done");

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Park.class);
                i.putExtra("a", "notScan");
                i.putExtra("b", "dummy");
                startActivity(i);

                            }
        });
    }




    }


