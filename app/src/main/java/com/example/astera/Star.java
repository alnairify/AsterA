package com.example.astera;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Eurie on 6/22/2017.
 */

public class Star {

    private double x;
    private double y;
    private Bitmap bmp;
    private GameView gameview;
    private double xspeed=0;
    private double currentFrame=0;
    private int width;
    private int columnWidth = 4;

    public Star(GameView gameview, Bitmap bmp, float x, float y){
        this.x=x;
        this.y=y;
        this.gameview=gameview;
        this.bmp=bmp;
        this.width=bmp.getWidth()/columnWidth;
    }

    public void update(){
        x+=xspeed;
        currentFrame+=1%(columnWidth-1);
    }

    public void onDraw(Canvas canvas){
        int srcX= (int) (currentFrame*width);
    }
}
