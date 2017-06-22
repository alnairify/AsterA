package com.example.astera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
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

    Bitmap playerbmp;
    private List<Player> player = new ArrayList<Player>();



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
        player.add(new Player(this,playerbmp,50,50));
        Log.d("TEST", "gv"+player.size());
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



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        for(Player pplayer: player)
        {
            pplayer.onDraw(canvas);
            ///Log.d("TEST","confused");
        }

    }

}

