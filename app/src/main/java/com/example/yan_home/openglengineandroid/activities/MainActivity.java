package com.example.yan_home.openglengineandroid.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.yan_home.openglengineandroid.R;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        (new Handler()).postDelayed(new Runnable() {
            @Override
            public void run() {

                System.gc();

                //TODO : load assets here
                Intent myIntent = new Intent(MainActivity.this, GameActivity.class);
                //Optional parameters
//                myIntent.putExtra("key", value);
                MainActivity.this.startActivity(myIntent);

                //TODO : custom transition animation ?
//                overridePendingTransition();

                //we no longer need this activity
                finish();
            }
        }, 2000);

    }

}
