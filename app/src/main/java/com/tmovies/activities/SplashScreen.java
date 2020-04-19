package com.tmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.tmovies.R;
import com.tmovies.db.SQLiteDatabaseHelper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.deleteDatabase(SQLiteDatabaseHelper.DATABASE_NAME);
        int DELAY = 2000;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, HomeScreen.class);
                startActivity(intent);

            }
        }, DELAY);
        
    }
}
