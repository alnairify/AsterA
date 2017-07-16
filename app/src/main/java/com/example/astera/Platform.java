package com.example.astera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Eurie on 7/12/2017.
 */

public class Platform{
    private Bitmap bmp;
    private GameView gv;
    private int x;
    private int y;
    private Rect playerr;
    private Rect platr;

    public Platform (GameView gv, Bitmap bmp, int x, int y){
        this.gv=gv;
        this.bmp = bmp;
        this.x=x;
        this.y=y;

    }

    public void update(){
        x-=gv.globalxSpeed;
    }
    public int returnX(){
        return x;
    }
    public int returnY(){
        return y;
    }

    public void draw(Canvas canvas) {
        try{canvas.drawBitmap(bmp,x,y,null);
            //coin.draw(canvas);
        }
        catch(Exception e){};
    }


    public boolean checkCollision(Rect playerr, Rect platr){
        this.playerr=playerr;
        this.platr=platr;

        return Rect.intersects(playerr,platr);
    }

    public Rect GetBounds() {
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
