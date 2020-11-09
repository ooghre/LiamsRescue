package com.example.liamsrescue;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;

/*
* This class is the activity that hows the View of the game while it is being played
* */
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

    /*
    * This method controls what happens if the user goes back
    * Go back to Main activity if the user goes back
    * */
    @Override
    public void onBackPressed() {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

}