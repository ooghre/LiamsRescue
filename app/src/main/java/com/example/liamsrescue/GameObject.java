package com.example.liamsrescue;

import android.graphics.Bitmap;
import android.graphics.Rect;

import java.util.Random;

/*
* This is the parent class of all objects in the games like traps, bombs and lives
* */
public abstract class GameObject {

    protected int x, y;
    protected int speed;
    protected int speedIncrease;
    protected Rect hitBox;
    protected int maxX;
    protected int maxY;

    // Constructor
    public GameObject( int screenX, int screenY) {
        Random generator = new Random();
        speed = generator.nextInt(15)+10;
        maxX = screenX;
        maxY = screenY;
        speedIncrease = 15;
    }

    /*
    * getter for the speed
    * */
    public int getSpeed() {
        return speed;
    }

    /*
     * getter for the x
     * */
    public int getX() {
        return x;
    }

    /*
     * getter for the y
     * */
    public int getY() {
        return y;
    }

    /*
     * getter for the hitbox of the bitmap
     * */
    public Rect getHitBox() {
        return hitBox;
    }

    /*
     * This method updates the position of the hitbox with the updated x and y
     * */
    protected void updateHitBox(Bitmap bitmap) {
        // Refresh hit box location
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }
}


