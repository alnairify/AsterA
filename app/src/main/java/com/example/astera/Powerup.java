package com.example.astera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Eurie on 6/26/2017.
 */

public class Powerup {
    GameView gameview;
    Bitmap bmp;
    int x,y,y2;
    private Rect playerr;
    private Rect pur;

    public Powerup(GameView gameview, Bitmap bmp, int x, int y){
        this.gameview=gameview;
        this.bmp=bmp;
        this.x=x;
        this.y=y;
    }

    public void update() {

        x-=gameview.globalxSpeed;
        y=gameview.getHeight()-Ground.height-bmp.getHeight()-y2;
    }

    public int returnX(){
        return x;
    }

    public boolean checkCollision(Rect playerr, Rect pur){
        this.playerr=playerr;
        this.pur=pur;

        return Rect.intersects(playerr,pur);
    }


    public Rect GetBounds(){
        return new Rect(this.x,this.y,this.x+bmp.getWidth(),this.y+bmp.getHeight());
    }

    public void onDraw(Canvas canvas){
        update();
        int srcX= bmp.getWidth();
        Rect src = new Rect(0, 0, srcX, bmp.getHeight());
        Rect dst = new Rect(x, y,x+bmp.getWidth(),y+bmp.getHeight());
        canvas.drawBitmap(bmp, src,dst,null);
    }

}
