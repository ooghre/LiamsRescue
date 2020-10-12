package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class Obstacles {

    private int x, y;
    private int speed = 0;

    // Constructor
    public Obstacles(Context context) {
        x = 50;
        y = 50;
        speed = 1;
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

    public void update() {
        y++;
    }
}

