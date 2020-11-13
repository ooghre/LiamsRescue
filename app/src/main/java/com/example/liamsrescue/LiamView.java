package com.example.liamsrescue;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import androidx.core.view.MotionEventCompat;
import java.io.IOException;
import java.util.Random;

/*
*   This class contains the MAin View for the game as well as logic for he game control
*   It contains a canvas on which we draw game object
*   It also contains the game loop which controls playing the game, controlling frame rate and drawing to the canvas
* */
public class LiamView extends SurfaceView implements Runnable {

    private Context context;

    //screen size
    private int screenX;
    private int screenY;

    volatile boolean playing = true;
    Thread gameThread = null;

    private PlayerCharacter player;

    // For drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder ourHolder;

    //game objects
    private Spikes spike1;
    private Spikes spike2;
    private Spikes spike3;
    private Bomb bomb;
    private Life life;
    private Random createLife = new Random();

    //game values
    private int score;
    private int highestScore;
    private boolean gameEnded;

    //for controlling sound
    private SoundPool soundPool;
    private int bump = -1;

    //for storing high score
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public LiamView(Context context, int x, int y) {
        super(context);
        this.context = context;

        //initialize soundpool for bump
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try{
            AssetManager assetManager = context.getAssets();
            AssetFileDescriptor descriptor;
            descriptor = assetManager.openFd("bump.ogg");
            bump = soundPool.load(descriptor, 0);
        }catch(IOException e){
            Log.e("error", "failed to load sound file");
        }

        // Get a reference to a file called HiScores.
        // If id doesn't exist one is created
        prefs = context.getSharedPreferences("HiScores",
                context.MODE_PRIVATE);

        editor = prefs.edit();
        highestScore =  prefs.getInt("higest score", 0);

        screenX = x;
        screenY = y;
        ourHolder = getHolder();
        paint = new Paint();
        startGame();
    }

    /*
    * This method initializes the game objects
    * It also starts or restarts the game by setting score to 0 and game ended to false
    * */
    private void startGame(){
        player = new PlayerCharacter(context, screenX, screenY);
        spike1 = new Spikes(context, screenX, screenY);
        spike2 = new Spikes(context, screenX, screenY);
        spike3 = new Spikes(context, screenX, screenY);
        bomb = new Bomb(context, screenX,screenY);
        life = new Life(context, screenX, screenY);

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

    /*
    * This method pauses the game thread if the user goes to the home page
    * */
    public void pause(){
        playing = false;
        try {
            gameThread.join();
        }
        catch (InterruptedException e) {
            Log.w("warning", e.getMessage());
        }
    }

    /*
    * This method controls the games response to user touch
    * If the player touches the screen it starts the game
    * If the player removes his hand from the screen it ends the game
    * If the player moves on the screen it mooves the charcter with the player
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        switch(action) {
            case (MotionEvent.ACTION_DOWN) :    //start game when the player touches screen
                if(gameEnded)
                    startGame();
                return true;

            case (MotionEvent.ACTION_MOVE) :    //move the charcter along with the players finger
                player.setX(event.getX() - 100);
                player.setY(event.getY() - 250);
                player.updateHitBox();
                draw();
                return true;

            case (MotionEvent.ACTION_UP) :      //end the game if the player lifts off their finger from game

                if(! gameEnded) //if the game isn't over play the sound to let the user know that its now over
                    soundPool.play(bump, 1, 1, 0, 0, 1);
                gameEnded = true;
                if (score > highestScore) { //reset high score if user beats high score
                    //play new high score sound
                    editor.putInt("higest score", score);
                    editor.commit();
                    highestScore = score;
                }

                return true;
            default :
                return super.onTouchEvent(event);
        }
    }

    /*
    * This method resumes the game thread when the user goes back to the game after a pause
    * */
    public void resume() {
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    /*
    * This method checks for collisions between player and game objects
    * If it detects a collision it removes the object from the screen and adds or removes from the players lives
    * This method also ends the game if the players lives reach 0
    * This method also keeps track and updates the players score
    * */
    private void update(){
        boolean hit = false;
        if(player.getHitBox().intersect(spike1.getHitBox())){
            hit = true;
            spike1.setY(10000);  //if spike hits player, place spike on random location outside screen
            player.reduceLives();
        }
        if(player.getHitBox().intersect(spike2.getHitBox())){
            hit = true;
            spike2.setY(10000);   //if spike hits player, place spike on random location outside screen
            player.reduceLives();
        }
        if(player.getHitBox().intersect(spike3.getHitBox())){
            hit = true;
            spike3.setY(10000);   //if spike hits player, place spike on random location outside screen
            player.reduceLives();
        }
        if(player.getHitBox().intersect(bomb.getHitBox())){
            hit = true;
            bomb.setY(10000);  //if bomb hits player, place bomb on random location outside screen

            player.reduceLives();
        }

        if(player.getHitBox().intersect(life.getHitBox())){
            life.setY(100000);  //if life hits player, place life object on random location outside screen
            player.increaseLives();
        }

        if(hit){
            if(!gameEnded) { // play sound to let the user know there was a bump
                soundPool.play(bump, 1, 1, 0, 0, 1);
            }
            if(player.getNumLives() ==0) {
                gameEnded = true;

                if (score > highestScore) {     //reset high score if user beats high score
                    //play new high score sound
                    editor.putInt("higest score", score);
                    editor.commit();
                    highestScore = score;
                }
            }
        }

        if(!gameEnded) {    //update score
            score += spike1.update();
            score += spike2.update();
            score += spike3.update();
            score += bomb.update();
            life.update();

            // If life object is out of screen spawn a new life object using probability 4/1000
            if(createLife.nextInt(1000) % 300 ==0 && life.getY()> screenY ){
                life.setY(-100000);  //respawn life by setting Y to negative
            }
        }
    }

    /*
    * This method draws objects on the screen
    * It also draws the high score, num of lives and current score
    * This method also draws a game over screen when the user exhaust all lives or lifts finger from screen
    * */
    private void draw(){
        if(ourHolder.getSurface().isValid()){

            canvas = ourHolder.lockCanvas();
            canvas.drawColor(Color.argb(255, 0, 0, 0));
            paint.setColor(Color.argb(255, 255, 255, 255));

            //draw game objects and characters on screen
            canvas.drawBitmap(player.getBitmap(), player.getX(), player.getY(), paint);
            canvas.drawBitmap(spike1.getBitmap(), spike1.getX(), spike1.getY(), paint);
            canvas.drawBitmap(spike2.getBitmap(), spike2.getX(), spike2.getY(), paint);
            canvas.drawBitmap(spike3.getBitmap(), spike3.getX(), spike3.getY(), paint);
            canvas.drawBitmap(bomb.getBitmap(), bomb.getX(), bomb.getY(), paint);
            canvas.drawBitmap(life.getBitmap(), life.getX(), life.getY(), paint);

            if(!gameEnded) {
                // Draw the hud
                paint.setTextAlign(Paint.Align.LEFT);
                paint.setColor(Color.argb(255, 255, 255, 255));
                paint.setTextSize(30);
                canvas.drawText("   HighSCore:" + highestScore, 1, 20, paint);
                canvas.drawText("Score:" + score, screenX / 4, 20,
                        paint);
                canvas.drawText("Lives left:" + player.getNumLives(), screenX / 2, 20,
                        paint);
            }
            else{
                // Show Game over screen
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

    /*
    * This method controls the frame rate of the game
    * It sets the frame rate to 60 (1000/17) frames pe rsecond
    * */
    private void control(){
        try {
            gameThread.sleep(17); //sleep for 17 seconds to set framerate
        }
        catch (InterruptedException e) {
            Log.w("warning", e.getMessage());
        }
    }
}
