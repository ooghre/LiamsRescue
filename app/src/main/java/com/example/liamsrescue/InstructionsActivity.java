package com.example.liamsrescue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

/*
* This method contains the activity that shows the game instructions page
* */
public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
    }

    /*
    * This method sends the user to the game activity class when the user presses play
    * */
    public void play(View v) {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }

    /*
     * This method sends the user to the main activity class when the user presses play
     * */
    @Override
    public void onBackPressed() {
        // your code.
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
