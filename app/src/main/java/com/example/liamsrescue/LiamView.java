package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class LiamView extends SurfaceView implements Runnable {

    volatile boolean playing = true;
    Thread gameThread = null;
    private PlayerCharacter player;
    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    public LiamView(Context context) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint();
        player = new PlayerCharacter(context);
    }

    @Override
    public void run(){
        while(playing){
            update();
            draw();
            control();
        }
    }

    public void pause(){
        playing = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException e) {

        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update(){
        player.update();
    }

    private void draw(){
        if (ourHolder.getSurface().isValid()) {
//First we lock the area of memory we will be drawing to
            canvas = ourHolder.lockCanvas();
// Rub out the last frame
            canvas.drawColor(Color.argb(255, 0, 0, 0));
// Draw the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);
// Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try {
            gameThread.sleep(17);
        }
        catch (InterruptedException e) {
        }
    }
}
