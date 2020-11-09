package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/*
*  This class represent the bomb objects on the screen.
* It is a subclass of the gameObject class
* */
public class Bomb extends GameObject {

    // Detect enemies leaving the screen

    private Bitmap first_bitMap; //bitmap for bomb before it explodes
    private Bitmap second_bitMap;  // bitmap for bomb after it explodes
    private int randBomb;  //random number to control when bomb explodes

    // Constructor
    public Bomb(Context context, int screenX, int screenY) {
        super(screenX, screenY);
        first_bitMap =  BitmapFactory.decodeResource(context.getResources(), R.drawable.explode_1);
        second_bitMap =  BitmapFactory.decodeResource(context.getResources(), R.drawable.explode_2);
        Random generator = new Random();
        y = 0;
        x = generator.nextInt(maxX) - first_bitMap.getHeight();  //let x start at a random position
        hitBox = new Rect(x,y, x + first_bitMap.getWidth(), y + first_bitMap.getHeight());
        randBomb = generator.nextInt(maxY);
    }

    /*
     * Method to return the bitmap of the bomb
     * Note there are 2 bit maps associated with a bomb 1st bimap, bomb before esploding
     * 2nd bitmap, bomb after exploding
     * We return first or second bitmap based on whether the position of the bomb is greater than a random number
     * */
    public Bitmap getBitmap(){
        Bitmap val = null;
        Random generator = new Random();

        if(y<=randBomb){  //we at a random location "higher" in the screen. Return first bitmap
            val = first_bitMap;
        }
        else{  //we at a random location "lower" in the screen. Return second bitmap
            val = second_bitMap;
        }

        return val;
    }

    /*
     * setter for Y
     * */
    public void setY(int y) {
        this.y = y;
    }

    /*
     * This method updates the position of the bomb
     * It returns 1 if the bomb reaches the bottom of the screen and is respawned else it returns 0
     * This method also updates the position of the hitbox
     * */
    public int update(){
        int respawned =0;
        y += speed; //move the bomb down

        //respawn when off screen
        if(y > maxY){
            Random generator = new Random();
            speed = generator.nextInt(10)+10;  //give the respawned bomb a new random speed
            x = generator.nextInt(maxX-first_bitMap.getWidth());
            y = 0;
            randBomb = generator.nextInt(maxY); //rest randBom
            respawned = 1; //set respawned to 1
        }
        updateHitBox(this.getBitmap());
        return  respawned;
    }

}






