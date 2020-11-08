package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

public class Life extends GameObject {

    private Bitmap bitmap;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;
    private Rect hitBox;

    // Constructor
    public Life(Context context, int screenX, int screenY) {

        super(context);
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.heart);
        minY = 0;
        minX = 0;
        maxX = screenX;
        maxY = screenY;

        Random generator = new Random();
        speed = generator.nextInt(6)+10;
        y = 0;
        x = generator.nextInt(maxX) - bitmap.getHeight();
        //x = 500;
        //y = 500;
        hitBox = new Rect(x,y, bitmap.getWidth(), bitmap.getHeight());

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
        if(y<0){
            Random generator = new Random();
            speed = generator.nextInt(15)+ speedIncrease;
            speedIncrease++;
            x = generator.nextInt(maxX-bitmap.getWidth());
            y = 0;
            respawned =1;
        }

        // Refresh hit box location
        hitBox.left = x;
        hitBox.top = y;
        hitBox.right = x + bitmap.getWidth();
        hitBox.bottom = y + bitmap.getHeight();
        return  respawned;
    }
}
