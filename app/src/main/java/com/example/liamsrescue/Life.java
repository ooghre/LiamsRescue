package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import java.util.Random;

/*
*  This class represent the Life objects on the screen.
* It is a subclass of the gameObject class
 */
public class Life extends GameObject {

    private Bitmap bitmap;

    // Constructor
    public Life(Context context, int screenX, int screenY) {
        super( screenX, screenY);
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.heart);

        Random generator = new Random();
        y = 0;
        x = generator.nextInt(maxX) - bitmap.getHeight();
        hitBox = new Rect(x,y, x+bitmap.getWidth(), y+bitmap.getHeight());

    }

    /*
     * Method to return the bitmap of the life object
     * */
    public Bitmap getBitmap() {
        return bitmap;
    }

    /*
     * setter for Y
     * */
    public void setY(int y) {
        this.y = y;
    }

    /*
     * This method updates the position of the life object
     * This mathod gradually increases the speed of the life object by incrementing the speedIncrease each iteration
     * This method also updates the position of the hitbox
     * */
    public void update(){
        y += speed;

        //respawn when off screen
        if(y<0){
            Random generator = new Random();
            speed = generator.nextInt(15)+ speedIncrease;
            speedIncrease++;  //gradually increase the speed of object
            x = generator.nextInt(maxX-bitmap.getWidth());
            y = 0;
        }

        // Refresh hit box location
        updateHitBox(bitmap);
    }
}
