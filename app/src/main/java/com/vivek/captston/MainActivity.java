package com.vivek.captston;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends Activity {
    private static int SPLASH_TIME_OUT=1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent=new Intent(MainActivity.this,Login.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
