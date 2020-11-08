package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;

import java.util.Random;

public class Bomb extends Obstacles {

    // Detect enemies leaving the screen
    private int maxX;
    private int minX;
    // Spawn enemies within screen bounds
    private int maxY;
    private int minY;
    private Rect hitBox;

    private Bitmap first_bitMap;
    private Bitmap second_bitMap;
    private Bitmap third_bitMap;
    private int randBomb;

    // Constructor
    public Bomb(Context context, int screenX, int screenY) {

        super(context);
        first_bitMap =  BitmapFactory.decodeResource(context.getResources(), R.drawable.explode_1);
        second_bitMap =  BitmapFactory.decodeResource(context.getResources(), R.drawable.explode_2);
        minY = 0;
        minX = 0;
        maxX = screenX;
        maxY = screenY;
        Random generator = new Random();
        speed = generator.nextInt(6)+10;
        y = 0;
        x = generator.nextInt(maxX) - first_bitMap.getHeight();
        hitBox = new Rect(x,y, first_bitMap.getWidth(), first_bitMap.getHeight());
        randBomb = generator.nextInt(maxY);

    }

    public int getSpeed() {
        return getSpeed();
    }

    public Bitmap getBitMap(){
        Bitmap val = null;
        Random generator = new Random();
        int explode = generator.nextInt(maxY);

        if(y<=randBomb){
            val = first_bitMap;
        }
        else{
            val = second_bitMap;
        }

        return val;
    }

    public int getX() {
        return super.getX();
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return super.getY();
    }

    public Rect getHitBox() {
        return hitBox;
    }

    public int update(){
        int respawned =0;
        y += speed;

        //respawn when off screen
        if(y > maxY || y<0){
            Random generator = new Random();
            speed = generator.nextInt(10)+10;
            x = generator.nextInt(maxX-first_bitMap.getWidth());
            y = 0;
            randBomb = generator.nextInt(maxY);
            respawned = 1;
        }

        // Refresh hit box location
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + this.getBitMap().getWidth();
        hitBox.bottom = y + this.getBitMap().getHeight();
        return  respawned;
    }
}






