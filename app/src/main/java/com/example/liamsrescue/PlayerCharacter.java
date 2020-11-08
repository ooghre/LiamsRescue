package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;
import android.util.DisplayMetrics;

public class PlayerCharacter {

    private Bitmap bitmap;
    private int x, y;
    int maxY;
    int minY;
    private int speed = 0;
    private Rect hitBox;
    private int numLives;

    // Constructor
    public PlayerCharacter(Context context, int screenX, int screenY) {
        int x = screenX/4;
        int y = 1850;
        bitmap = BitmapFactory.decodeResource
                (context.getResources(), R.drawable.character);

        maxY = screenY - bitmap.getHeight();
        minY = 0;
        hitBox = new Rect(x,y, bitmap.getWidth(), bitmap.getHeight());
        numLives = 3;

    }

    public int getSpeed() {
        return speed;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getNumLives(){
        return numLives;
    }

    public void reduceLives(){
        numLives--;
    }

    public void increaseLives(){
        numLives++;
    }

    public void setY(float newY) {
        y = (int)newY;
    }
    public void setX(float newX) {
        x = (int)newX;
    }
    public void update() {
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
