package com.example.psrivastava12.parking;


import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Calendar;
import java.util.Date;


public class Park extends Activity implements OnClickListener
{
    EditText ProfileID,Name,Details,Balance,Slot;
    TextView BalText, SlotText;
    int money=0,left=0,time;
    Button Insert,Delete,Update,View,ViewAll,addd,Slots;
    SQLiteDatabase db;


    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);





        ProfileID=(EditText)findViewById(R.id.ProfileID);
        Slots = findViewById(R.id.viewSlots);
        Name=(EditText)findViewById(R.id.Name);
        Details=(EditText)findViewById(R.id.Details);
        Balance = findViewById(R.id.Balance);
        Balance.setVisibility(View.INVISIBLE);
        BalText = findViewById(R.id.BalText);
        BalText.setVisibility(View.INVISIBLE);
        addd = (Button)findViewById(R.id.addd);
        addd.setVisibility(View.INVISIBLE);
        Insert=(Button)findViewById(R.id.Insert);
        Delete=(Button)findViewById(R.id.Delete);
        Update=(Button)findViewById(R.id.Update);
        View=(Button)findViewById(R.id.View);
        ViewAll=(Button)findViewById(R.id.ViewAll);
        SlotText=findViewById(R.id.slotText);
        SlotText.setVisibility(View.INVISIBLE);
        Slot=findViewById(R.id.slot);
        Slot.setVisibility(View.INVISIBLE);

        Insert.setOnClickListener(this);
        Delete.setOnClickListener(this);
        Update.setOnClickListener(this);
        View.setOnClickListener(this);
        ViewAll.setOnClickListener(this);
        addd.setOnClickListener(this);
        Slots.setOnClickListener(this);


        // Creating database and table
        db =openOrCreateDatabase("ProfileDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS Profile(ProfileID VARCHAR,name VARCHAR,Details VARCHAR, Balance INT);");
        db.execSQL("CREATE TABLE IF NOT EXISTS Slot(Slot VARCHAR, ProfileID VARCHAR,Time VARCHAR,State VARCHAR);");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {

            db.execSQL("INSERT INTO Slot VALUES(' " + "1" + " ' ,'" + -1 + "','" + -1 + "','" + 0 + "' ),(' " + "2" + " ' ,'" + -1 + "','" + -1 + "','" + 0 + "'),(' " + "3" + " ' ,'" + -1 + "','" + -1 + "','" + 0 + "' ),(' " + "4" + " ' ,'" + -1 + "','" + -1 + "','" + 0 + "' ),(' " + "5" + " ' ,'" + -1 + "','" + -1 + "','" + 0 + "' );");

            // mark first time has ran.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }



        Intent i = getIntent();
        String id = i.getStringExtra("a");


        //showMessage("a", id);

        if (id.equalsIgnoreCase("scan") ) {
            String proid = i.getStringExtra("b");
            //showMessage("a", "in");
            ProfileID.setText("");
            Name.setText("");
            Details.setText("");
            Balance.setText("");
            ProfileID.requestFocus();
            Insert.setVisibility(View.INVISIBLE);
            Slots.setVisibility(android.view.View.INVISIBLE);
            addd.setVisibility(View.INVISIBLE);
            Update.setVisibility(View.INVISIBLE);
            View.setVisibility(View.INVISIBLE);
            ViewAll.setVisibility(View.INVISIBLE);
            Delete.setVisibility(View.INVISIBLE);
            addd.setVisibility(android.view.View.INVISIBLE);
            Slot.setVisibility(android.view.View.VISIBLE);
            SlotText.setVisibility(android.view.View.VISIBLE);
            Cursor f = db.rawQuery("SELECT * FROM Profile WHERE ProfileID='"+proid+"'", null);
            if (f.moveToFirst()) {
                Balance.setVisibility(View.VISIBLE);
                BalText.setVisibility(View.VISIBLE);
                Cursor ff = db.rawQuery("SELECT * FROM Profile WHERE ProfileID='"+proid+"'", null);
                if (ff.moveToFirst()) {
                ProfileID.setText(ff.getString(0));
                Name.setText(ff.getString(1));
                Details.setText(ff.getString(2));
                Balance.setText(ff.getString(3));}
                else{
                    showMessage("Error","Cannot fill");
                }
                Cursor g = db.rawQuery("SELECT * FROM Slot WHERE ProfileID = '"+proid+"'", null);

                if (g.moveToFirst()) {

                    String alot = g.getString(0);
                    showMessage("Success", "Exited Successful");
                    Slot.setVisibility(android.view.View.INVISIBLE);
                    SlotText.setText("Exit");
                    String EntryTime = g.getString(2);
                    Calendar rightNow = Calendar.getInstance();
                    //int Exittime =  rightNow.get(Calendar.HOUR_OF_DAY);
                    int Exittime =  rightNow.get(Calendar.SECOND);
                    time = Exittime - Integer.parseInt(EntryTime);
                   // left = Integer.parseInt(f.getString(3)) - 20;
                    left = calcFare(time);
                    left = Integer.parseInt(f.getString(3)) - left;
                    db.execSQL("UPDATE Profile SET Balance =" + left + " WHERE ProfileID='" + proid+ "'");


                    db.execSQL("UPDATE SLot SET ProfileID =" + -1 + ",Time =" + -1 + ",State =" + 0 + " WHERE Slot='" +alot+ "'");

                    Balance.setText(String.valueOf(left));

                }
                else {

                    Cursor x = db.rawQuery("SELECT * FROM Slot WHERE State='"+0+"'", null);
                    if (x.moveToFirst())
                    {
                        String newalot = x.getString(0);
                        Slot.setText(newalot);
                        Calendar rightNow = Calendar.getInstance();
                        //int entryTime =  rightNow.get(Calendar.HOUR_OF_DAY);
                        int entryTime =  rightNow.get(Calendar.SECOND);
                        db.execSQL("UPDATE SLot SET ProfileID =" + proid + ",Time =" + String.valueOf(entryTime) + ",State =" + 1 + " WHERE Slot='" +newalot+ "'");
                        showMessage("Success", "Entered Successful");
                    }

                    else{
                        showMessage("Error", "Parking Full");
                        Slot.setVisibility(android.view.View.INVISIBLE);
                        SlotText.setText("Full");
                    }
                }

            }
            else{
                showMessage("Error","Invalid RFID");
            }

        }


    }

    public void onClick(View view) {
        // Inserting a record to the Profile table



            if (view == Insert) {
                Log.d("hi","234------------------------------------------------------------------------------------");
            Balance.setVisibility(View.INVISIBLE);
            BalText.setVisibility(View.INVISIBLE);
            addd.setVisibility(View.INVISIBLE);
            // Checking for empty fields
            if (ProfileID.getText().toString().trim().length() == 0 ||
                    Name.getText().toString().trim().length() == 0 ||
                    Details.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter all values");
                return;
            }
            db.execSQL("INSERT INTO Profile VALUES('" + ProfileID.getText() + "','" + Name.getText() +
                    "','" + Details.getText() + "',0);");
            showMessage("Success", "Record added");
            clearText();
        }
        // Deleting a record from the Profile table
        if (view == Delete) {
            Balance.setVisibility(View.INVISIBLE);
            BalText.setVisibility(View.INVISIBLE);
            addd.setVisibility(View.INVISIBLE);
            // Checking for empty roll number
            if (ProfileID.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter ProfileID");
                return;
            }
            Cursor c = db.rawQuery("SELECT * FROM Profile WHERE ProfileID='" + ProfileID.getText() + "'", null);
            if (c.moveToFirst()) {
                db.execSQL("DELETE FROM Profile WHERE ProfileID='" + ProfileID.getText() + "'");
                showMessage("Success", "Record Deleted");
            } else {
                showMessage("Error", "Invalid ProfileID");
            }
            clearText();
        }
        // Updating a record in the Profile table
        if (view == Update) {
            Balance.setVisibility(View.INVISIBLE);
            BalText.setVisibility(View.INVISIBLE);
            addd.setVisibility(View.INVISIBLE);
            // Checking for empty roll number
            if (ProfileID.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter ProfileID");
                return;
            }
            Cursor c = db.rawQuery("SELECT * FROM Profile WHERE ProfileID='" + ProfileID.getText() + "'", null);
            if (c.moveToFirst()) {
                db.execSQL("UPDATE Profile SET name='" + Name.getText() + "',Details='" + Details.getText() +
                        "' WHERE ProfileID='" + ProfileID.getText() + "'");
                showMessage("Success", "Record Modified");
            } else {
                showMessage("Error", "Invalid ProfileID");
            }
            clearText();
        }

        if (view == addd) {

            // Checking for empty roll number
            if (ProfileID.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter ProfileID");
                return;
            }
            Cursor d = db.rawQuery("SELECT * FROM Profile WHERE ProfileID='" + ProfileID.getText() + "'", null);
            if (d.moveToFirst()) {
                money = Integer.parseInt(Balance.getText().toString());
                money = money + Integer.parseInt(d.getString(3));
                db.execSQL("UPDATE Profile SET Balance =" + money + " WHERE ProfileID='" + ProfileID.getText() + "'");
                showMessage("Success", "Added Successful");
            } else {
                showMessage("Error", "Invalid ProfileID");
            }
            addd.setVisibility(android.view.View.INVISIBLE);
            Balance.setVisibility(View.INVISIBLE);
            BalText.setVisibility(View.INVISIBLE);
            clearText();

        }
        // Display a record from the Profile table
        if (view == View) {
            // Checking for empty roll number
            if (ProfileID.getText().toString().trim().length() == 0) {
                showMessage("Error", "Please enter ProfileID");
                return;
            }
            Cursor c = GetData(ProfileID);
            if (c.moveToFirst()) {
                Balance.setVisibility(View.VISIBLE);
                BalText.setVisibility(View.VISIBLE);
                addd.setVisibility(View.VISIBLE);
                Name.setText(c.getString(1));
                Details.setText(c.getString(2));
                Balance.setText(c.getString(3));
            } else {
                showMessage("Error", "Invalid ProfileID");
                clearText();
            }
        }
        // Displaying all the Profiles
        if (view == ViewAll) {
            Cursor c = db.rawQuery("SELECT * FROM Profile", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("ProfileID: " + c.getString(0) + "\n");
                buffer.append("Name: " + c.getString(1) + "\n");
                buffer.append("Details: " + c.getString(2) + "\n");
                buffer.append("Wallet Balance: " + c.getString(3) + "\n\n");
            }
            showMessage("Profile Details", buffer.toString());
        }


        // Displaying all the Slots
        if (view == Slots) {
            Cursor c = db.rawQuery("SELECT * FROM Slot", null);
            if (c.getCount() == 0) {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer = new StringBuffer();
            while (c.moveToNext()) {
                buffer.append("Slot: " + c.getString(0) + "\n");
                buffer.append("ProfileID: " + c.getString(1) + "\n");
                buffer.append("Time: " + c.getString(2) + "\n");
                buffer.append("State: " + c.getString(3) + "\n\n");
            }
            showMessage("Profile Details", buffer.toString());
        }

    }

    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public Cursor GetData(EditText A)
    {
               return db.rawQuery("SELECT * FROM Profile WHERE ProfileID='"+A.getText()+"'", null);
    }



    public void clearText()
    {

        ProfileID.setText("");
        Name.setText("");
        Details.setText("");
        Balance.setText("");
        ProfileID.requestFocus();

    }
    public int calcFare(int h)
    {
        if (h<= 3)
        {
            return 30;
        }
        else
        {
            h = ((h-3)*10)+30;
        }
        return h;
    }
}