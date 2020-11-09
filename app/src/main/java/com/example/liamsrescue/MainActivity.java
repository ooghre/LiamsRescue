package com.example.liamsrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void play(View v){
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }

    public void openInstructions(View v) {
        Intent i = new Intent(this, InstructionsActivity.class);
        startActivity(i);
        finish();
    }
}