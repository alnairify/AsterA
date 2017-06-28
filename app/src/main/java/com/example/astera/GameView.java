package com.example.astera;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Eurie on 6/21/2017.
 */

public class GameView extends SurfaceView {
    GameLoopThread gameLoopThread;
    SurfaceHolder holder;

    public static int globalxSpeed = 8;

    Bitmap playerbmp;
    Bitmap starbmp;
    Bitmap gbmp;
    Bitmap spikesbmp;
    Bitmap pbmp;
    int xx=0;

    private List<Star> star = new ArrayList<Star>();
    private List<Player> player = new ArrayList<Player>();
    private List<Ground> ground = new ArrayList<Ground>();
    private List<Spikes> spikes = new ArrayList<Spikes>();
    private List<Powerup> pu = new ArrayList<Powerup>();

    public static int Score = 0;
    public static int HighScore=0;
    public static int achievementScore10000=0; //false
    private int lastscore=0;

    private String saveAchievementScore10000 = "Achievement: scored 10000";

    private boolean shielded =false;

    private static SharedPreferences prefs;

    public static int Starscollected =0;

    private int ptimerShield=200;
    private int timerShield=0;
    private int timerRandomShield=0;
    private int timerStars = 0;
    private int timerSpikes =0;
    private int timerRandomSpikes=1;
    private String saveScore = "Highscore";
    private String Menu="Running";

    public GameView(Context context) {
        super(context);
        prefs=context.getSharedPreferences("com.example.AsterA",context.MODE_PRIVATE);

        String spackage = "com.example.AsterA";

        HighScore = prefs.getInt(saveScore, 0);
        achievementScore10000=prefs.getInt(saveAchievementScore10000,0);

        gameLoopThread = new GameLoopThread(this);

        holder = getHolder();
        holder.addCallback(new Callback() { // CTRL + Space tar farm allt

            public void surfaceDestroyed(SurfaceHolder arg0) {
                // TODO Auto-generated method stub
                Score =0;
                Starscollected=0;
                prefs.edit().putInt(saveScore,HighScore).commit();
                prefs.edit().putInt(saveAchievementScore10000,achievementScore10000).commit();
                gameLoopThread.running = false;
            }

            public void surfaceCreated(SurfaceHolder arg0) {
                // TODO Auto-generated method stub
                gameLoopThread.setRunning(true);
                gameLoopThread.start();


            }

            public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub

            }

        });
        playerbmp = BitmapFactory.decodeResource(getResources(), R.drawable.player1);
        starbmp = BitmapFactory.decodeResource(getResources(), R.drawable.str);
        gbmp = BitmapFactory.decodeResource(getResources(), R.drawable.g);
        spikesbmp=BitmapFactory.decodeResource(getResources(),R.drawable.spikes);
        pbmp=BitmapFactory.decodeResource(getResources(),R.drawable.pu);

        player.add(new Player(this,playerbmp,50,50));
        pu.add(new Powerup(this,pbmp,600,32));
        spikes.add(new Spikes(this,spikesbmp, 400,0));
        spikes.add(new Spikes(this,spikesbmp, 800,0));
        star.add(new Star(this,starbmp,120,50));
        star.add(new Star(this,starbmp,50,50));
        //Log.d("TEST", "gv");
        // TODO Auto-generated constructor stub
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        for(Player pplayer: player)
        {
            pplayer.ontouch();
        }

        if(Menu=="Mainmenu"){
            Menu="Running";
            startGame();

        }
        return false;


    }

    public void update(){
        if(Menu =="Running") {
            Score += 5;
            lastscore = Score;
            updatetimers();
            deleteground();
            if (Score >= 10000 && achievementScore10000 == 0) {
                achievementScore10000 = 1;
            }
            if (Score > HighScore) {

                HighScore = Score;
            }
        }
    }

    public void updatetimers(){
        timerStars++;
        timerSpikes++;
        timerShield++;

        if(Menu=="Running"){
        if(shielded){
            ptimerShield--;
            if(ptimerShield<=0){
                shielded=false;
            }
        }

        switch(timerRandomShield){
            case 0:
                if(timerShield>=150){
                    pu.add(new Powerup(this,pbmp, this.getWidth()+32,0));
                    Random randomShield = new Random();
                    timerRandomShield = randomShield.nextInt(3);
                    timerShield=0;
                }
                break;
            case 1:
                if(timerShield>=250){
                    pu.add(new Powerup(this,pbmp, this.getWidth()+32,0));
                    Random randomShield = new Random();
                    timerRandomShield = randomShield.nextInt(3);
                    timerShield=0;
                }
                break;
            case 2:
                if(timerShield>=350){
                    pu.add(new Powerup(this,pbmp, this.getWidth()+32,0));
                    Random randomShield = new Random();
                    timerRandomShield = randomShield.nextInt(3);
                    timerShield=0;
                }
                break;

        }



        switch(timerRandomSpikes){
            case 0:
            if(timerSpikes>=75){
                spikes.add(new Spikes(this,spikesbmp,this.getWidth()+32,0));
                Random randomSpikes=new Random();
                timerRandomSpikes = randomSpikes.nextInt(3);
                timerSpikes=0;
            }
                break;
            case 1:
                if(timerSpikes>=125){
                    spikes.add(new Spikes(this,spikesbmp,this.getWidth()+32,0));
                    Random randomSpikes=new Random();
                    timerRandomSpikes = randomSpikes.nextInt(3);
                    timerSpikes=0;
                }break;
            case 2:
                if(timerSpikes>=100){
                    spikes.add(new Spikes(this,spikesbmp,this.getWidth()+32,0));
                    Random randomSpikes=new Random();
                    timerRandomSpikes = randomSpikes.nextInt(3);
                    timerSpikes=0;
                }break;




        }

        if(timerStars>=50) {
            Random randomStar = new Random();
            int random;
            random = randomStar.nextInt(3);

            switch (random) {

                case 1:
                    int currentstar = 1;
                    int xx = 1;

                    while (currentstar <= 5) {
                        star.add(new Star(this, starbmp, this.getWidth() + (32 * xx), 32));
                        currentstar++;
                        xx++;
                    }
                    break;

                case 2:
                    currentstar = 1;
                    star.add(new Star(this, starbmp, this.getWidth() + 32, 32));
                    star.add(new Star(this, starbmp, this.getWidth() + 64, 40));
                    star.add(new Star(this, starbmp, this.getWidth() + 96, 32));
                    star.add(new Star(this, starbmp, this.getWidth() + 128, 40));
                    star.add(new Star(this, starbmp, this.getWidth() + 160, 32));
                    //break;

                /*case 3:
                    currentstar = 1;
                    xx=1;

                    while(currentstar<=5){
                        star.add(new Star(this, starbmp, this.getWidth()+(32*xx), 32));
                        currentstar++;
                        xx++;
                    }
                    break;*/


            }
            timerStars = 0;
        }

        }
    }

    public void addground(){
        while(xx<this.getWidth()+Ground.width){
            ground.add(new Ground(this,gbmp,xx,0));
            xx+= gbmp.getWidth();
        }
    }

    public void deleteground(){
        for(int i=ground.size()-1;i>=0;i--){
            int groundx=ground.get(i).returnX();
            if(groundx<=-Ground.width){
                //Log.d("TEST2", ""+groundx);
                //Log.d("TEST3", ""+Ground.width);
                ground.remove(i);
                ground.add(new Ground(this, gbmp,groundx+this.getWidth()+Ground.width,0));
            }
        }
    }

    public void endGame(){
        Menu = "Mainmenu";
        timerStars=0;
        timerShield=0;
        timerSpikes=0;

        for(int i=0;i<star.size();i++){
            star.remove(i);
        }
        for(int i=0;i<spikes.size();i++){
            spikes.remove(i);
        }
        for(int i=0;i<pu.size();i++){
            pu.remove(i);
        }
        player.remove(0);
    }

    public void startGame(){
        player.add(new Player(this,playerbmp,50,50));
    }

    @Override
    protected void onDraw(Canvas canvas) {

        update();
        canvas.drawColor(Color.WHITE);
        if(Menu=="Running") {
            addground();
            Paint textpaint = new Paint();
            textpaint.setTextSize(32);

            canvas.drawText("Score: " + Score, 600, 32, textpaint);
            canvas.drawText("High Score: " + HighScore, 600, 64, textpaint);
            canvas.drawText("Stars: " + Starscollected, 600, 96, textpaint);
            if (achievementScore10000 == 0) {
                canvas.drawText("Achievement 10000 score status: not fulfilled", 600, 128, textpaint);
            } else if (achievementScore10000 == 1) {
                canvas.drawText("Achievement 10000 score status: achieved!", 600, 128, textpaint);
            }

            for (Ground gground : ground) {
                gground.onDraw(canvas);
            }
            for (Player pplayer : player) {
                pplayer.onDraw(canvas);
                ///Log.d("TEST","confused");
            }
            for (int i = 0; i < spikes.size(); i++) {

                spikes.get(i).onDraw(canvas);
                Rect playerr = player.get(0).GetBounds();
                Rect spikesr = spikes.get(i).GetBounds();

                if (spikes.get(i).returnX() < -32) {
                    spikes.remove(i);

                } else if (spikes.get(i).checkCollision(playerr, spikesr)) {
                    if (!shielded) {
                        spikes.remove(i);
                        Score = 0;
                        Starscollected = 0;
                        endGame();
                        //break;

                    } else {
                        spikes.remove(i);
                        shielded = false;
                    }

                }
            }
            for (int i = 0; i < star.size(); i++) {
                Rect playerr = new Rect();
                star.get(i).onDraw(canvas);
                if(Menu!="Mainmenu"){
                    playerr = player.get(0).GetBounds();
                }

                Rect starr = star.get(i).GetBounds();

                if (star.get(i).returnX() < -32) {
                    star.remove(i);

                } else if (star.get(i).checkCollision(playerr, starr)) {
                    star.remove(i);
                    Score += 500;
                    Starscollected += 1;

                }
            }


            for (int i = 0; i < pu.size(); i++) {

                pu.get(i).onDraw(canvas);
                Rect playerr = player.get(0).GetBounds();
                Rect pur = pu.get(i).GetBounds();

                if (pu.get(i).returnX() < -32) {
                    pu.remove(i);

                } else if (pu.get(i).checkCollision(playerr, pur)) {
                    pu.remove(i);
                    shielded = true;
                    ptimerShield = 120;

                }
            }

        }

        if (Menu=="Mainmenu"){
            Paint textpaint = new Paint();
            textpaint.setTextSize(32);
            canvas.drawText(("Score: " + lastscore), canvas.getWidth()/2, canvas.getHeight()/2, textpaint);
        }

    }

}

