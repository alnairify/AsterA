package com.example.astera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Eurie on 6/26/2017.
 */

public class Spikes {

    private int x,y;
    private Bitmap bmp;
    private GameView gameview;
    private Rect playerr;
    private Rect spikesr;

    public Spikes(GameView gameview, Bitmap bmp, int x, int y){

        this.gameview=gameview;
        this.bmp=bmp;
        this.x=x;
        this.y=y;

    }

    public boolean checkCollision(Rect playerr, Rect spikesr){
        this.playerr=playerr;
        this.spikesr=spikesr;

        return Rect.intersects(playerr,spikesr);
    }

    public int returnX(){
        return x;
    }

    public Rect GetBounds(){
        return new Rect(this.x,this.y,this.x+bmp.getWidth(),this.y+bmp.getHeight());
    }

    public void update(){
        x-=gameview.globalxSpeed;
        y=gameview.getHeight()-Ground.height-bmp.getHeight();
    }

    public void onDraw(Canvas canvas){
        update();
        int srcX= bmp.getWidth();
        Rect src = new Rect(0, 0, srcX, bmp.getHeight());
        Rect dst = new Rect(x, y,x+bmp.getWidth(),y+bmp.getHeight());
        canvas.drawBitmap(bmp, src,dst,null);
    }
}
