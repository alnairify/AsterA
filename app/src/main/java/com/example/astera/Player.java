package com.example.astera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by Eurie on 6/21/2017.
 */

public class Player {
    static int x;
    static int y;
    static int gravity = (int) 1.2;
    static int vspeed = 1;
    static int playerheight;
    static int playerwidth;
    static int jumppower = -10;
    private int width;
    private int height;

    private int columnWidth=1;
    private int columnHeight=1;

    Rect playerr;
    Bitmap bmp;
    GameView gameview;
    private int i;
    private Star star;

    public Player(GameView gameview, Bitmap bmp, int x, int y)
    {
        this.x = x;
        this.y = y;
        this.gameview = gameview;
        this.bmp = bmp;
        playerheight=bmp.getHeight();

        this.width=bmp.getWidth()/columnWidth;
        this.height=bmp.getHeight()/columnHeight;
    }
    public void update(){
        checkground();

    }

    public void checkground(){
        if (y < gameview.getHeight()-64-playerheight){
            vspeed+=gravity;
            if (y > gameview.getHeight()-64-playerheight-vspeed)
            {
                vspeed = gameview.getHeight()-64-y-playerheight;
            }
        }
        else if (vspeed>0)
        {
            vspeed = 0;
            y = gameview.getHeight()-64-playerheight;
        }
        y += vspeed;
    }

    public void ontouch(){
        if (y>= gameview.getHeight()-64-playerheight)
        {
            vspeed = jumppower;
        }
    }


    public Rect GetBounds(){
        return new Rect(this.x,this.y,this.x+width,this.y+height);
    }

    public void onDraw(Canvas canvas){
        Log.d("TEST", "pl");
        update();
        canvas.drawBitmap(bmp, x, y, null);

    }
}

