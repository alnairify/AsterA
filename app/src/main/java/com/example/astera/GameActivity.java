package com.example.astera;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {
    private GameView gameView;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "ga");
        gameView=new GameView(this);
        setContentView(gameView); /*Sets the displaycontent to our new object GameView*/
    }

    @Override
    public void onPause(){
        super.onPause();;
        gameView.gameLoopThread.running=false;
        finish();
    }
}


