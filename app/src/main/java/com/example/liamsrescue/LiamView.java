package com.example.liamsrescue;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import androidx.core.view.MotionEventCompat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LiamView extends SurfaceView implements Runnable {

    private Context context;
    private int screenX;
    private int screenY;
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

    private float timeRemaining;
    private int score;
    private int highestScore;
    boolean gameEnded;

    private SoundPool soundPool;
    int bump = -1;

    public LiamView(Context context, int x, int y) {
        super(context);
        this.context = context;

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd("bump.ogg");
            bump = soundPool.load(descriptor, 0);
        }catch(IOException e){
            Log.e("error", "failed to load sound file");
        }

        screenX = x;
        screenY = y;
        ourHolder = getHolder();
        paint = new Paint();
        startGame();
    }

    private void startGame(){

        player = new PlayerCharacter(context, screenX, screenY);

        spike1 = new Spikes(context, screenX, screenY);
        spike2 = new Spikes(context, screenX, screenY);
        spike3 = new Spikes(context, screenX, screenY);
        bomb = new Bomb(context, screenX,screenY);

        // Reset score
        score = 0;
        gameEnded = false;
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
            Log.w("warning", e.getMessage());
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                if(gameEnded)
                    startGame();

                return true;
            case (MotionEvent.ACTION_MOVE) :
                player.setX(event.getX() - 100);
                player.setY(event.getY() - 250);
                player.update();
                draw();
                return true;
            case (MotionEvent.ACTION_UP) :
                if(! gameEnded) soundPool.play(bump, 1, 1, 0, 0, 1);
                gameEnded = true;

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
        boolean hit = false;
        if(player.getHitBox().intersect(spike1.getHitBox())){
            hit = true;
            spike1.setY(10000);
        }
        if(player.getHitBox().intersect(spike2.getHitBox())){
            hit = true;
            spike2.setY(10000);
        }
        if(player.getHitBox().intersect(spike3.getHitBox())){
            hit = true;
            spike3.setY(10000);
        }
        if(player.getHitBox().intersect(bomb.getHitBox())){
            hit = true;
            bomb.setY(-10000);
        }

        if(hit){
            if(!gameEnded) {
                soundPool.play(bump, 1, 1, 0, 0, 1);}
            gameEnded = true;

            if (score> highestScore) {
                highestScore = score;
            }
        }

        if(!gameEnded) {
            score += spike1.update(player.getSpeed());
            score += spike2.update(player.getSpeed());
            score += spike3.update(player.getSpeed());
            score += bomb.update();
            timeRemaining -= player.getSpeed();
        }

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
            canvas.drawBitmap
                    (bomb.getBitMap(),
                            bomb.getX(),
                            bomb.getY(), paint);

            if(!gameEnded) {
                // Draw the hud
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(30);
                canvas.drawText("   HighSCore:" + highestScore, 1, 20, paint);
                canvas.drawText("Score:" + score, screenX / 4, 20,
                        paint);
            }
            else{
                // Show pause screen
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                canvas.drawText("Game Over", screenX/2, 100, paint);
                paint.setTextSize(25);
                canvas.drawText("HighSCore:"+ highestScore, screenX/2, 160, paint);
                canvas.drawText("Score:" + score, screenX / 2, 200, paint);
                paint.setTextSize(80);
                canvas.drawText("Tap to replay!", screenX/2, 350, paint);
            }

            // Unlock and draw the scene
            ourHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void control(){
        try {
            gameThread.sleep(17);
        }
        catch (InterruptedException e) {
            Log.w("warning", e.getMessage());
        }
    }
}
