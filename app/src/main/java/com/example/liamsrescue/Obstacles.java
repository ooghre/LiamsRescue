package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class Obstacles {

    protected int x, y;
    protected int speed;
    protected int speedIncrease;

    // Constructor
    public Obstacles(Context context) {
        x = 50;
        y = 50;
        speed = 5;
        speedIncrease = 15;
    }

    public int getSpeed() {
        return speed;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


