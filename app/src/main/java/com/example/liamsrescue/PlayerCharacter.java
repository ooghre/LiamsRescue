package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/*
*  This class represent the character on the screen.
 */
public class PlayerCharacter {

    private Bitmap bitmap;
    private int x, y;
    int maxY;
    int minY;
    private Rect hitBox;
    private int numLives;

    // Constructor
    public PlayerCharacter(Context context, int screenX, int screenY) {

        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.character);

        x = screenX/2;
        y = screenY-bitmap.getHeight();
        maxY = screenY - bitmap.getHeight();
        minY = 0;
        hitBox = new Rect(x,y, x+bitmap.getWidth(), y+bitmap.getHeight());
        numLives = 3;

    }

    /*
     * Method to return the bitmap of the life object
     * */
    public Bitmap getBitmap() {
        return bitmap;
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
     * getter for numLives
     * */
    public int getNumLives(){
        return numLives;
    }

    /*
     * reduce numLives by 1
     * */
    public void reduceLives(){
        numLives--;
    }

    /*
     * increase num LIves by 1
     * */
    public void increaseLives(){
        numLives++;
    }

    /*
     * setter for the y
     * */
    public void setY(float newY) {
        y = (int)newY;
    }

    /*
     * setter for the x
     * */
    public void setX(float newX) {
        x = (int)newX;
    }

    /*
     * this method updates the hitbox of the player
     * */
    public void updateHitBox() {
        // Refresh hit box location
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
    }

    public Rect getHitBox() {
        return hitBox;
    }
}
