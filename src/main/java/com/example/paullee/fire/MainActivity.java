package com.example.paullee.fire;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.TextView;
import java.util.Timer;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends Activity {

    float xVelocity;
    float maxVelocity;
    public VelocityTracker tracker = null;
    public TextView textViewV, textViewMax, textFire;
    boolean started = false;
    long startTime = 0;
    GifImageView flames;
    GifImageView sparkling;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flames = (GifImageView) findViewById(R.id.flames);
        flames.setVisibility(View.INVISIBLE);

        sparkling = (GifImageView) findViewById(R.id.sparkling);
        sparkling.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int action = event.getActionMasked();

        switch(action) {
            case MotionEvent.ACTION_DOWN:
                if (tracker == null) {
                    flames.setVisibility(View.INVISIBLE);
                    sparkling.setVisibility(View.INVISIBLE);
                    tracker = VelocityTracker.obtain();
                    xVelocity = 0;
                    startTime = System.currentTimeMillis();
                } else {
                    tracker.clear();
                    flames.setVisibility(View.INVISIBLE);
                    sparkling.setVisibility(View.INVISIBLE);
                    tracker = VelocityTracker.obtain();
                    xVelocity = 0;
                    startTime = System.currentTimeMillis();
                }
            case MotionEvent.ACTION_MOVE:
                tracker.addMovement(event);
                tracker.computeCurrentVelocity(1000);
                float xVelocity = Math.abs(tracker.getXVelocity());
                if(xVelocity > 50){
                    sparkling.setVisibility(View.VISIBLE);
                }
                if (xVelocity > 100) {
                    long millis = System.currentTimeMillis() - startTime;
                    int seconds = (int) (millis / 1000);

                    started = true;
                    if (seconds > 1) {
                        sparkling.setVisibility(View.INVISIBLE);
                        flames.setVisibility(View.VISIBLE);
                    }
                } else {
                    flames.setVisibility(View.INVISIBLE);
                    if (started) {
                        startTime = System.currentTimeMillis();
                    }
                }
                if (xVelocity == 0) {
                    flames.setVisibility(View.INVISIBLE);
                    maxVelocity = 0;
                    while (xVelocity != 0) {
                        maxVelocity = xVelocity;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                startTime = System.currentTimeMillis();
                started = false;
                flames.setVisibility(View.INVISIBLE);
                sparkling.setVisibility(View.INVISIBLE);
                break;
            case MotionEvent.ACTION_CANCEL:
                tracker.recycle();
                break;
        }
        return true;
    }
}
