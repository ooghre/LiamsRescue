package com.example.liamsrescue;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.core.view.MotionEventCompat;

import java.util.ArrayList;
import java.util.List;

public class LiamView extends SurfaceView implements Runnable {

    volatile boolean playing = true;
    Thread gameThread = null;
    private PlayerCharacter player;
    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;
    //private int numTraps = 3;
    //List<Spikes> spikesList = new ArrayList<Spikes>();
    private Spikes spike1;
    private Spikes spike2;
    private Spikes spike3;
    private Bomb bomb;






    public LiamView(Context context, int x, int y) {
        super(context);
        ourHolder = getHolder();
        paint = new Paint();
        player = new PlayerCharacter(context, x, y);

        spike1 = new Spikes(context, x, y);
        spike2 = new Spikes(context, x, y);
        spike3 = new Spikes(context, x, y);
        bomb = new Bomb(context,x,y);
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


    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d( "test","Action was DOWN");

                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d("test","Action was MOVE");
                player.setX(event.getX());
                player.setY(event.getY());
                player.update();
                draw();
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d("test","Action was UP");
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d("test","Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d("test","Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    private void update(){

        if(player.getHitBox().intersect(spike1.getHitBox())){
            spike1.setY(10000);
        }
        if(player.getHitBox().intersect(spike2.getHitBox())){
            spike2.setY(10000);
        }
        if(player.getHitBox().intersect(spike3.getHitBox())){
            spike3.setY(10000);
        }
        if(player.getHitBox().intersect(bomb.getHitBox())){
            bomb.setY(10000);
        }

        //player.update();
        //if(onTouchEvent(event))


        spike1.update(player.getSpeed());
        spike2.update(player.getSpeed());
        spike3.update(player.getSpeed());
        bomb.update(player.getSpeed());
    }

    private void draw(){
        if(ourHolder.getSurface().isValid()){
            canvas = ourHolder.lockCanvas();
            //clear screen
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            // White specs of dust
            paint.setColor(Color.argb(255, 255, 255, 255));
            //Draw the dust from our arrayList

            // Draw the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);
            canvas.drawBitmap
                    (spike1.getBitmap(),
                            spike1.getX(),
                            spike1.getY(), paint);
            canvas.drawBitmap
                    (spike2.getBitmap(),
                            spike2.getX(),
                            spike2.getY(), paint);
            canvas.drawBitmap
                    (spike3.getBitmap(),
                            spike3.getX(),
                            spike3.getY(), paint);
            bomb.getAnimation().start();
            bomb.getAnimation().draw(canvas);

            System.out.println(bomb.getAnimation().isRunning());
            System.out.println(bomb.getAnimation().isVisible());

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
