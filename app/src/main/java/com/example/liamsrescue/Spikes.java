package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Spikes extends GameObject {

    private Bitmap bitmap;

    // Constructor
    public Spikes(Context context, int screenX, int screenY) {

        super( screenX, screenY);
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.spikes);
        Random generator = new Random();
        y = 0;
        x = generator.nextInt(maxX) - bitmap.getHeight();
        hitBox = new Rect(x,y, x+bitmap.getWidth(), y+bitmap.getHeight());

    }

    /*
     * Method to return the bitmap of the spike object
     * */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /*
     * Setter for y
     * */
    public void setY(int y) {
        this.y = y;
    }

    /*
     * This method updates the position of the spike
     * It returns 1 if the spike reaches the bottom of the screen and is respawned else it returns 0
     * This method also updates the position of the hitbox
     * */
    public int update(){
        int respawned =0;
        y += speed;

        //respawn when off screen
        if(y > maxY){
            Random generator = new Random();
            speed = generator.nextInt(15)+ speedIncrease;
            speedIncrease++; // gradually increase the speed of the spikes
            x = generator.nextInt(maxX-bitmap.getWidth());
            y = 0;
            respawned =1;
        }

        // Refresh hit box location
        updateHitBox(bitmap);
        return  respawned;
    }
}






