package com.example.astera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Eurie on 6/22/2017.
 */

public class Star {

    private int x;
    private int y;
    private int y2;
    private Bitmap bmp;
    private GameView gameview;
    private int xspeed=-GameView.globalxSpeed;
    private int currentFrame=0;
    private int width;
    private int height;
    private int columnWidth = 4;
    private int columnHeight = 1;

    private Rect playerr;
    private Rect strr;

    public Star(GameView gameview, Bitmap bmp, int x, int y){
        this.x=x;
        this.y2=y;
        this.gameview=gameview;
        this.bmp=bmp;
        this.width=bmp.getWidth()/columnWidth;
        this.height = bmp.getHeight()/columnHeight;
    }

    public void update() {

        x += xspeed;
        y=gameview.getHeight()-y2-Ground.height-bmp.getHeight();
        if (currentFrame >= columnWidth - 1) {
            currentFrame = 0;
        } else{
            currentFrame += 1;
        }


        //currentFrame+=1%(columnWidth-1);
    }

    public int returnX(){
        return x;
    }

    public Rect GetBounds(){
        return new Rect(this.x,this.y,this.x+width,this.y+height);
    }

    public void onDraw(Canvas canvas){
        update();
        int srcX= (int) (currentFrame*width);
        Rect src = new Rect((int) currentFrame*width, 0, srcX+width, 32);
        Rect dst = new Rect(x,y,x+width,y+32);
        canvas.drawBitmap(bmp, src,dst,null);
    }


    public boolean checkCollision(Rect playerr, Rect strr){
        this.playerr=playerr;
        this.strr=strr;

        return Rect.intersects(playerr,strr);
    }
}
