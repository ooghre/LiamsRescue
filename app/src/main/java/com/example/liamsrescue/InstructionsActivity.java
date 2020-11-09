package com.example.liamsrescue;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class InstructionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_instruction);
    }


    public void play(View v) {
        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onBackPressed() {
        // your code.
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
