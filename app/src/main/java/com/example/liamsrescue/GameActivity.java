package com.example.liamsrescue;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;

public class GameActivity extends Activity {
    // This is where the "Play" button from HomeActivity sends us
    private LiamView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Display display = getWindowManager().getDefaultDisplay();
        // Load the resolution into a Point object
        Point size = new Point();
        display.getSize(size);
        gameView = new LiamView(this, size.x, size.y);
        setContentView(gameView);

    }
    // If the Activity is paused make sure to pause the thread
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    // If the Activity is resumed make sure to resume the thread
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

}