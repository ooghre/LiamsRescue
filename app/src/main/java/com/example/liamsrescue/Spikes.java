package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Spikes extends Obstacles {

    private Bitmap bitmap;

    // Constructor
    public Spikes(Context context) {

        super(context);
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.spikes);
    }

    public int getSpeed() {
        return getSpeed();
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return super.getX();
    }

    public int getY() {
        return super.getY();
    }

    public void update() {
        super.update();
    }
}

