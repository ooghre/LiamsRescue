package com.example.liamsrescue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        //Here we set our UI layout as the view
        setContentView(R.layout.activity_main);
// Get a reference to the button in our layout
        final Button buttonPlay =
                (Button)findViewById(R.id.buttonPlay);
// Listen for clicks
        buttonPlay.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }
}