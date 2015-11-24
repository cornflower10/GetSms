package com.example.cornflower.getsms;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
     private EditText et;
    private SmsContent smsContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et = (EditText) findViewById(R.id.et);
       smsContent = new SmsContent(new Handler(),MainActivity.this,et);
       getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContent);
    }

    @Override
    protected void onDestroy() {
        getContentResolver().unregisterContentObserver(smsContent);
        super.onDestroy();
    }
}
