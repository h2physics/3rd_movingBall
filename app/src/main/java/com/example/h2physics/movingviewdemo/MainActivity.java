package com.example.h2physics.movingviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{
    Button button;
    ViewGroup viewGroup;
    int xDelta;
    int yDelta;
    int viewWidth;
    int viewHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = (Button) findViewById(R.id.button);
        viewGroup = (RelativeLayout) findViewById(R.id.root);

        viewWidth = button.getWidth();
        viewHeight = button.getHeight();

        button.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int X = (int) event.getRawX();
        //get original X coordinate of this event
        final int Y = (int) event.getRawY();
        //get original Y coordinate of this event

        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:{
                //happen when event starts
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();
                //clone layout of view

                xDelta = X - layoutParams.leftMargin;
                yDelta = Y - layoutParams.topMargin;
                //get delta distance which view moves

                break;
            }

            case MotionEvent.ACTION_MOVE:{
                //happen when view move, before ACTION_UP and after ACTION_DOWN
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) v.getLayoutParams();

                layoutParams.leftMargin = X - xDelta;
                layoutParams.topMargin = Y - yDelta;
                //set left & top margin for this layout
                layoutParams.rightMargin = -viewWidth;
                layoutParams.bottomMargin = - viewHeight;
                //set right & bottom margin so that view isn't changed when move to right & bottom size

                if (layoutParams.leftMargin < 0){
                    layoutParams.leftMargin = 0;
                }
                if (layoutParams.topMargin < 0){
                    layoutParams.topMargin = 0;
                }
                if (layoutParams.leftMargin > (viewGroup.getWidth() - v.getWidth())){
                    layoutParams.leftMargin = (viewGroup.getWidth() - v.getWidth());
                }
                if (layoutParams.topMargin > (viewGroup.getHeight() - v.getHeight())){
                    layoutParams.topMargin = (viewGroup.getHeight() - v.getHeight());
                }

                v.setLayoutParams(layoutParams);

                break;
            }

            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
        }

        viewGroup.invalidate();
        return true;
    }
}
