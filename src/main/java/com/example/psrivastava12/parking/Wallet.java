package com.example.psrivastava12.parking;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.example.psrivastava12.parking.Park;

public class Wallet extends Activity implements android.view.View.OnClickListener {
    EditText WProfileID, WName, WDetails, WBalance;
    TextView WBalText;
    Button  WView, WAdd;


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);



        WProfileID = (EditText) findViewById(R.id.WProfileID);
        WName = (EditText) findViewById(R.id.WName);
        WDetails = (EditText) findViewById(R.id.WDetails);
        WBalance = findViewById(R.id.WBalance);
        WBalance.setVisibility(View.INVISIBLE);
        WBalText = findViewById(R.id.WBalText);
        WBalText.setVisibility(View.INVISIBLE);
        WView = (Button) findViewById(R.id.ViewBal);
        WAdd = findViewById(R.id.add);


        WView.setOnClickListener(this);
        WAdd.setOnClickListener(this);

        }

    public void onClick(View view) {
        // Inserting a record to the Profile table
        Park p = new Park();
        if (view == WView) {
            // Checking for empty roll number
            if(WProfileID.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter ProfileID");
                return;
            }
            Cursor c= p.GetData(WProfileID);
            if(c.moveToFirst())
            {
                WBalance.setVisibility(View.VISIBLE);
                WBalText.setVisibility(View.VISIBLE);
                WName.setText(c.getString(1));
               WDetails.setText(c.getString(2));
                WBalance.setText(c.getString(3));
            }
            else
            {
                showMessage("Error", "Invalid ProfileID");
                clearText();
            }
        }

        if (view == WAdd) {
        }







    }

    public void showMessage(String title,String message)
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        WProfileID.setText("");
        WName.setText("");
        WDetails.setText("");
        WProfileID.requestFocus();
    }

}