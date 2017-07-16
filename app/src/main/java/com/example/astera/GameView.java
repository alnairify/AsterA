package com.example.astera;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;

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
    Bitmap platbmp;
    int xx=0;

    private List<Star> star = new ArrayList<Star>();
    private List<Player> player = new ArrayList<Player>();
    private List<Ground> ground = new ArrayList<Ground>();
    private List<Spikes> spikes = new ArrayList<Spikes>();
    private List<Platform> platform = new ArrayList<Platform>();
    private List<Powerup> pu = new ArrayList<Powerup>();

    public static int Score = 0;
    public static int HighScore=0;
    public static int achievementScore10000=0; //false
    private int lastscore=0;

    public static final int WIDTH = 247;
    public static final int HEIGHT = 106;

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
    private int timerplat=0;
    private int timerrandplat=0;

    private String saveScore = "Highscore";
    private String Menu="Running";


    int MAXX;
    int MAXY;

    public GameView(Context context) {
        super(context);
        prefs=context.getSharedPreferences("com.example.AsterA",context.MODE_PRIVATE);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        MAXX = size.x;
        MAXY = size.y;

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
                //timerplat = System.currentTimeMillis();
                //lastplatheight = 100;
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
        platbmp = BitmapFactory.decodeResource(getResources(),R.drawable.g);

        player.add(new Player(this,playerbmp,50,50));
        pu.add(new Powerup(this,pbmp,600,32));
        star.add(new Star(this,starbmp,120,50));
        star.add(new Star(this,starbmp,50,50));
        platform.add(new Platform(this, platbmp, 500, MAXY -175));
        platform.add(new Platform(this, platbmp, 532, MAXY-200));
        platform.add(new Platform(this, platbmp, 564, MAXY-200));
        platform.add(new Platform(this, platbmp, 596, MAXY-200));
        platform.add(new Platform(this, platbmp, 620, MAXY-200));
        platform.add(new Platform(this, platbmp, 654, MAXY-200));
        platform.add(new Platform(this, platbmp, 700, MAXY-200));
        platform.add(new Platform(this, platbmp, 754, MAXY-200));
        platform.add(new Platform(this, platbmp, 800, MAXY-200));
        platform.add(new Platform(this, platbmp, 1000, MAXY -275));
        platform.add(new Platform(this, platbmp, 1032, MAXY -275));
        platform.add(new Platform(this, platbmp, 1064, MAXY -275));

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

            /*Random randomplat = new Random();
            int rp;
            rp = randomplat.nextInt(3);

            long platformElapsed = (System.nanoTime() - timerplat) / 1000000;
            if (platformElapsed > 700) {
                //add platforms
                if (platform.size() == 0) {
                    //new Star(this, starbmp, this.getWidth() + 32, 32
                    platform.add(new Platform(this, platbmp, WIDTH - 300, lastplatheight));
                } else {
                    //platform from a certain distance so the person can jump
                    //but also have the possibility to miss
                    if (rp>1) {
                        if (lastplatheight == 100) {
                            lastplatheight = lastplatheight- 10;
                            platform.add(new Platform(this, platbmp, WIDTH, lastplatheight));

                        } else {
                            lastplatheight= lastplatheight + 10;
                            platform.add(new Platform(this, platbmp, WIDTH, lastplatheight));

                        }
                    } else {
                        if (lastplatheight == 10) {
                            lastplatheight= lastplatheight+ 10;
                            platform.add(new Platform(this, platbmp, WIDTH, lastplatheight));

                        } else {
                            lastplatheight = lastplatheight - 10;
                            platform.add(new Platform(this, platbmp, WIDTH, lastplatheight));

                        }
                    }
                }
                timerplat = System.nanoTime();
            }

            for (int i = 0; i < platform.size(); i++) {
                Rect playerr = player.get(0).GetBounds();
                Rect platr = platform.get(i).GetBounds();
                //update platform
                platform.get(i).update();

                if (platform.get(i).returnX() < -1000) {
                    platform.remove(i);
                    break;
                }

                //star.get(i).checkCollision(playerr, starr)
                //add collision check
                if (platform.get(i).checkCollision(playerr,platr)){
                    player.get(0).vspeed=0;
                }else{
                    player.get(0).vspeed=1;
                }

            }*/
        }
    }

    public void updatetimers(){
        timerStars++;
        timerSpikes++;
        timerShield++;
        timerplat++;

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

            switch(timerrandplat){
                case 0:
                    if(timerplat>=75){

                        platform.add(new Platform(this, platbmp, this.getWidth()+50, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+100, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+200, MAXY -300));
                        platform.add(new Platform(this, platbmp, this.getWidth()+250, MAXY -300));
                        platform.add(new Platform(this, platbmp, this.getWidth()+350, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+400, MAXY -200));
                        Random randomplat=new Random();
                        timerrandplat = randomplat.nextInt(3);
                        timerplat=0;
                    }
                    break;
                case 1:
                    if(timerplat>=125){
                        platform.add(new Platform(this, platbmp, this.getWidth()+50, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+100, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+150, MAXY -200));
                        Random randomplat=new Random();
                        timerrandplat = randomplat.nextInt(3);
                        timerplat=0;
                    }break;
                case 2:
                    if(timerplat>=100){
                        platform.add(new Platform(this, platbmp, this.getWidth()+50, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+100, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+150, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+250, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+300, MAXY -200));
                        platform.add(new Platform(this, platbmp, this.getWidth()+350, MAXY -200));
                        Random randomplat=new Random();
                        timerrandplat = randomplat.nextInt(3);
                        timerplat=0;
                    }break;




            }

           /* Random randomplat = new Random();
            int rp;
            rp = randomplat.nextInt(3);

            long platformElapsed = (System.nanoTime() - timerplat) / 1000000;
            if (platformElapsed > 700) {
                //add platforms
                if (platform.size() == 0) {
                    //new Star(this, starbmp, this.getWidth() + 32, 32
                    platform.add(new Platform(this, platbmp, WIDTH - 300, lastplatheight));
                } else {
                    //platform from a certain distance so the person can jump
                    //but also have the possibility to miss
                    if (rp>1) {
                        if (lastplatheight == 100) {
                            lastplatheight = lastplatheight- 10;
                            platform.add(new Platform(this, platbmp, WIDTH, lastplatheight));

                        } else {
                            lastplatheight= lastplatheight + 10;
                            platform.add(new Platform(this, platbmp, WIDTH, lastplatheight));

                        }
                    } else {
                        if (lastplatheight == 10) {
                            lastplatheight= lastplatheight+ 10;
                            platform.add(new Platform(this, platbmp, WIDTH, lastplatheight));

                        } else {
                            lastplatheight = lastplatheight - 10;
                            platform.add(new Platform(this, platbmp, WIDTH, lastplatheight));

                        }
                    }
                }
                timerplat = System.nanoTime();
            }

            for (int i = 0; i < platform.size(); i++) {
                Rect playerr = player.get(0).GetBounds();
                Rect platr = platform.get(i).GetBounds();
                //update platform
                platform.get(i).update();

                if (platform.get(i).returnX() < -1000) {
                    platform.remove(i);
                    break;
                }

                //star.get(i).checkCollision(playerr, starr)
                //add collision check
                if (platform.get(i).checkCollision(playerr,platr)){
                    player.get(0).vspeed=0;
                }else{
                    player.get(0).vspeed=1;
                }

            }*/


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
        for(int i=0;i<platform.size();i++){
            platform.remove(i);
        }


        player.remove(0);
    }

    public void startGame(){
        player.add(new Player(this,playerbmp,50,50));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //Button pbutton = (Button) findViewById(R.id.pause);
        //add(pbutton);
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

            for (int i = 0; i < platform.size(); i++) {
                platform.get(i).onDraw(canvas);

                Rect platr = platform.get(i).GetBounds();

                Rect playerr = new Rect();
                if(Menu!="Mainmenu"){
                    playerr = player.get(0).GetBounds();
                }

                //update platform

                if (platform.get(i).returnX() < -32) {
                    platform.remove(i);
                }else

                //star.get(i).checkCollision(playerr, starr)
                //add collision check
                if (platform.get(i).checkCollision(playerr, platr)) {
                    player.get(0).setY(platform.get(i).returnY()-platbmp.getHeight());
                    player.get(0).vspeed=0;
                    player.get(0).onPlat=true;
                } /*else {
                    player.get(0).vspeed = 1;
                }*/
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

