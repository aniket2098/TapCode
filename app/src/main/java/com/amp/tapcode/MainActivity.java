package com.amp.tapcode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int initialX;
    private int initialY;
    private int flag = 0;
    double degrees = 45.0;
    double radians = Math.toRadians(degrees);
    double sinValue = Math.sin(radians);
    double cosValue = Math.cos(radians);
    String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.touchView).setOnTouchListener(handleTouch);
    }

    private View.OnTouchListener handleTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int x = (int) event.getX();
            int y = (int) event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i("TAG", "down: (" + x + ", " + y + ")");

                    if(flag == 0) {
                        setInitialCenter(x, y);
                    } else {
                        password = password + getTapLocation(x, y);
                        TextView passwordTextView = (TextView)findViewById(R.id.passwordTextView);
                        passwordTextView.setText(password);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i("TAG", "moving: (" + x + ", " + y + ")");
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i("TAG", "touched up");
                    break;
            }
            return true;
        }
    };

    private void setInitialCenter(int x, int y) {

        initialX = (int)(x * cosValue - y * sinValue);
        initialY = (int)(x * sinValue + y * cosValue);
        flag = 1;
    }

    private int getTapLocation(int x, int y) {

        int transformedX = (int)(x * cosValue - y * sinValue) - initialX;
        int transformedY = (int)(x * sinValue + y * cosValue) - initialY;

        if(transformedX >= 0 && transformedY >= 0) {
            return 2;
        } else if(transformedX >= 0) {
            return 1;
        } else if(transformedY < 0) {
            return 4;
        } else {
            return 3;
        }
    }

}
