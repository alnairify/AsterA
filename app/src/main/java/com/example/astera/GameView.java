package com.example.astera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eurie on 6/21/2017.
 */

public class GameView extends SurfaceView {
    private GameLoopThread gameLoopThread;
    private SurfaceHolder holder;
    public static int globalxSpeed = 15;
    Bitmap playerbmp;
    Bitmap starbmp;

    private List<Star> star = new ArrayList<Star>();
    private List<Player> player = new ArrayList<Player>();

    public static int Score = 0;
    public static int HighScore=1000;

    public GameView(Context context) {
        super(context);

        gameLoopThread = new GameLoopThread(this);

        holder = getHolder();
        holder.addCallback(new Callback() { // CTRL + Space tar farm allt

            public void surfaceDestroyed(SurfaceHolder arg0) {
                // TODO Auto-generated method stub

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
        playerbmp = BitmapFactory.decodeResource(getResources(), R.drawable.player);
        starbmp = BitmapFactory.decodeResource(getResources(), R.drawable.str);

        player.add(new Player(this,playerbmp,50,50));
        star.add(new Star(this,starbmp,120,500));
        star.add(new Star(this,starbmp,50,600));
        Log.d("TEST", "gv");
        // TODO Auto-generated constructor stub
    }


    @Override
    public boolean onTouchEvent(MotionEvent e){
        for(Player pplayer: player)
        {
            pplayer.ontouch();
        }
        return false;

    }

    public void update(){
        Score +=5;

        if (Score > HighScore) {

            HighScore=Score;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        update();
        canvas.drawColor(Color.WHITE);

        Paint textpaint = new Paint();
        textpaint.setTextSize(32);

        canvas.drawText("Score: " + Score, 600, 32, textpaint);
        canvas.drawText("High Score: " + HighScore, 600, 64, textpaint);

        for(Player pplayer: player)
        {
            pplayer.onDraw(canvas);
            ///Log.d("TEST","confused");
        }
        for(int i=0; i<star.size();i++){

            star.get(i).onDraw(canvas);
            Rect playerr=player.get(0).GetBounds();
            Rect starr = star.get(i).GetBounds();

            if(star.get(i).checkCollision(playerr,starr)){
                star.remove(i);
                Score +=500;
            }
        }

    }

}

