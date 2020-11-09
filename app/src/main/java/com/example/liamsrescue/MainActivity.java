package com.example.liamsrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
     * This method sends the user to the game activity class when the user presses play
     * */
    public void play(View v){
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }

    /*
     * This method sends the user to the game instructions windows when the user presses play
     * */
    public void openInstructions(View v) {
        Intent i = new Intent(this, InstructionsActivity.class);
        startActivity(i);
        finish();
    }
}