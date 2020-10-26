package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;

import java.util.Random;

public class Bomb extends Obstacles {

    private AnimationDrawable animation;
    // Detect enemies leaving the screen
    private int maxX;
    private int minX;
    // Spawn enemies within screen bounds
    private int maxY;
    private int minY;
    private Rect hitBox;


    // Constructor
    public Bomb(Context context, int screenX, int screenY) {

        super(context);
        animation = (AnimationDrawable)context.getResources().getDrawable(R.drawable.animation_values);
        minY = 0;
        minX = 0;
        maxX = screenX;
        maxY = screenY;
        Random generator = new Random();
        speed = generator.nextInt(6)+10;
        y = 0;
        Rect bounds = animation.getFrame(0).getBounds();
        x = generator.nextInt(maxX) - bounds.width();
        //x = 500;
        //y = 500;
        hitBox = bounds;

    }

    public int getSpeed() {
        return getSpeed();
    }

    public AnimationDrawable getAnimation() {
        return animation;
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

    public void update() {
    }

    public void update(int playerSpeed){
        y += speed;
        Rect bounds = animation.getCurrent().getBounds();

        //respawn when off screen
        if(y > maxY){
            Random generator = new Random();
            speed = generator.nextInt(10)+10;

            x = generator.nextInt(maxX) - bounds.width();

            y = bounds.height();
        }

        // Refresh hit box location
        hitBox = bounds;
    }
}






