package com.example.astera;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class GameActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("TEST", "ga");
        setContentView(new GameView(this)); /*Sets the displaycontent to our new object GameView*/
    }
}

