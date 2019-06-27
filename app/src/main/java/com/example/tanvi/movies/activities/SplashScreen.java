package com.example.tanvi.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.tanvi.movies.R;
import com.example.tanvi.movies.utils.SQLiteDatabaseHelper;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.deleteDatabase(SQLiteDatabaseHelper.DATABASE_NAME);
        //delay in ms
        int DELAY = 2000;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MovieScreen.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_up);

            }
        }, DELAY);
        
    }
}
