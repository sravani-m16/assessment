package com.example.messenger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    SharedPreferences pref;
    Button read;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        read = findViewById(R.id.button2);

        lv =findViewById(R.id.lv);
        ActivityCompat.requestPermissions(MainActivity.this,
                new String[]{Manifest.permission.READ_SMS}, PackageManager.PERMISSION_GRANTED);

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Read_SMS(view);
            }
        });
    }

    @SuppressLint("Recycle")
    public void Read_SMS(View v) {

        Cursor cursor = null;

        try {
            cursor = getContentResolver().query(Uri.parse("content://sms/inbox"), null,
                    null, null, null, null);
            cursor.moveToFirst();

            pref = getSharedPreferences("application", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("SMS", cursor.getString(12));
            editor.apply();

        } catch (Exception e) {
            e.printStackTrace();
        }


       /* List<String> sms = new ArrayList<String>();
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        cursor = getContentResolver().query(uriSMSURI, null, null, null, null);

        while (cursor.moveToNext()) {
            @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
            String body = cursor.getString(cursor.getColumnIndexOrThrow("body"));
            sms.add("Number: " + address + " .Message: " + body);

        }
        return sms; */

        //tv.setText(cursor != null ? cursor.getString(12) : null);

        //lv = pref.getString("SMS", "defaultStringIfNothingFound");
        String sms = pref.getString("SMS","defaultStringIfNothingFound");

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add(sms);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.list_item,R.id.text,arrayList);
        lv.setAdapter(arrayAdapter);

    }
}