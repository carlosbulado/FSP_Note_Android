package com.carlosbulado.fsp_note.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.carlosbulado.fsp_note.R;
import com.carlosbulado.fsp_note.app.APP;
import com.carlosbulado.fsp_note.database.Database;
import com.carlosbulado.fsp_note.service.CategoryService;
import com.carlosbulado.fsp_note.service.NoteService;

public class SplashActivity extends AppCompatActivity
{
    @Override
    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        scheduleSplashScreen();
    }

    private void scheduleSplashScreen()
    {
        APP.context = getApplicationContext();
        long splashScreenDuration = getSplashScreenDuration();
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run ()
            {
                Database dbHelper = new Database(getApplicationContext());
                dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 0, 0);
                APP.Services.noteService = new NoteService(getApplicationContext());
                APP.Services.categoryService = new CategoryService(getApplicationContext());
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, splashScreenDuration);
    }

    private long getSplashScreenDuration() { return 2000L; }
}
