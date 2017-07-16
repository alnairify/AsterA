package com.example.astera;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

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
    static int jumppower = -15;
    private int width;
    private int height;
    private int currentFrame=0;
    private int animationpos=0;
    private int state =0;

    private int columnWidth=4;
    private int animationColumn=0;
    private int columnHeight=2;

    boolean onPlat= false;

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

        playerheight=bmp.getHeight()/2;

        this.width=bmp.getWidth()/columnWidth;
        this.height=bmp.getHeight()/columnHeight;
    }
    public void update(){
        checkground();

        checkanimationstate();
        switchanimations();


    }

    public void checkanimationstate(){
        if(vspeed<0){
            state=2;
        }else if(vspeed>0){
            state =1;
        }else{
            state=0;
        }
    }

    public void switchanimations(){
        if(state==0){
            animationColumn=4;
            animationpos=0;
            if (currentFrame >= (animationColumn-1)) {
                currentFrame = 0;
            } else{
                currentFrame += 1;
            }
        }else if (state==1){
            currentFrame=0;
            animationColumn=0;
            animationpos=1;
        }else if(state==2){
            currentFrame=1;
            animationpos=1;
            animationColumn=0;
        }

    }

    public void checkground(){
        if (y < gameview.getHeight()-Ground.height-playerheight){
            vspeed+=gravity;
            if (y > gameview.getHeight()-Ground.height-playerheight-vspeed)
            {
                vspeed = gameview.getHeight()-Ground.height-playerheight;
            }
        }
        else if (vspeed>0)
        {
            vspeed = 0;
            y = gameview.getHeight()-Ground.height-playerheight;
        }
        y += vspeed;
    }

    public void setY(int y){
        this.y=y;
    }

    public void ontouch(){
        if (y>= gameview.getHeight()-Ground.height-playerheight || onPlat)
        {
            vspeed = jumppower;
            onPlat=false;
        }

    }


    public Rect GetBounds(){
        return new Rect(this.x,this.y,this.x+width,this.y+height);
    }

    public void onDraw(Canvas canvas){
        //Log.d("TEST", "pl");
        update();

        int srcX= (int) (currentFrame*width);
        int srcY = animationpos*48; //1.5*sprite heigh
        Rect src = new Rect(srcX, srcY,srcX+width,srcY+width);
        Rect dst = new Rect(x,y,x+width,y+height);
        canvas.drawBitmap(bmp, src,dst,null);
    }
}

