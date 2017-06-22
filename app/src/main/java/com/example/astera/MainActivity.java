package com.example.astera;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void butStart(View view) {
        Log.d("TEST", "ma");
        Intent intent = new Intent(this, GameActivity.class);
        startActivity(intent);
    }

}
