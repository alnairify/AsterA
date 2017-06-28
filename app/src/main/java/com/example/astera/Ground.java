package com.example.astera;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Eurie on 6/25/2017.
 */



public class Ground {
    private GameView gv;
    private Bitmap bmp;
    private int x;
    private int y;
    public static int width;
    public static int height;

    public Ground (GameView gv, Bitmap bmp, int x, int y){
        this.gv=gv;
        this.bmp=bmp;
        this.x=x;
        this.y=y;
        this.width=bmp.getWidth();
        this.height =bmp.getHeight();
        //Log.d("TEST", ""+bmp.getWidth());
    }

    public void update(){
        x-=gv.globalxSpeed;
    }

    public int returnX(){
        return x;
    }

    public void onDraw(Canvas canvas){
        update();
        canvas.drawBitmap(bmp, x , gv.getHeight()-bmp.getHeight(),null);
    }
}
