package com.thelastrobmitch.newbostontutorial;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Robert on 5/09/2017.
 */

public class GFXSurface extends Activity implements View.OnTouchListener{

    MyBringBackSurface ourSurfaceView;
    float x, y, sX, sY, fX, fY, dX, dY, animateX, animateY, scaleX, scaleY;
    Bitmap test, plus;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ourSurfaceView = new MyBringBackSurface(this);
        ourSurfaceView.setOnTouchListener(this);
        x = 0;
        y = 0;
        sX = 0;
        sY = 0;
        fX = 0;
        fY = 0;
        dX = 0;
        dY = 0;
        animateX = 0;
        animateY = 0;
        scaleX = 0;
        scaleY = 0;
        test = BitmapFactory.decodeResource(getResources(), R.drawable.tennis75);
        plus = BitmapFactory.decodeResource(getResources(), R.drawable.plus);

        setContentView(ourSurfaceView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ourSurfaceView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ourSurfaceView.resume();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        // set up to sleep the thread so that we get only 30 frames per second
        // so sleep for 1000/30 = 33.333 milliseconds

        try {
            Thread.sleep(33);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        x = motionEvent.getX();
        y = motionEvent.getY();

        switch(motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                sX = motionEvent.getX();
                sY = motionEvent.getY();
                dX = dY = animateX = animateY = scaleY = scaleX = fX = fY = 0;
                break;
            case MotionEvent.ACTION_UP:
                fX = motionEvent.getX();
                fY = motionEvent.getY();
                dX = fX - sX;
                dY = fY - sY;
                scaleX = dX/30;
                scaleY = dY/30;
                x = y = 0;
                break;


        }

        return true;
    }

    class MyBringBackSurface extends SurfaceView implements Runnable{

        SurfaceHolder ourHolder;
        Thread ourThread = null;
        boolean isRunning = false;

        public MyBringBackSurface(Context context) {
            super(context);
            ourHolder = getHolder();
        }

        public void pause() {
            isRunning = false;
            while (true) {
                try {
                    ourThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                break;
            }
            ourThread = null;
        }

        public void resume(){
            isRunning = true;
            ourThread = new Thread(this);
            ourThread.start();
        }

        @Override
        public void run() {
            while (isRunning){
                if (!ourHolder.getSurface().isValid()) continue;
                Canvas canvas = ourHolder.lockCanvas();
                canvas.drawRGB(2, 2, 150);
                if(x != 0 && y != 0){
                    canvas.drawBitmap(test, x - test.getWidth()/2, y - test.getHeight()/2, null);
                }
                if(sX != 0 && sY != 0){
                    canvas.drawBitmap(plus, sX - plus.getWidth()/2, sY - plus.getHeight()/2, null);
                }
                if(fX != 0 && fY != 0){
                    canvas.drawBitmap(test, fX - (test.getWidth()/2) - animateX,
                            fY - (test.getHeight()/2)- animateY, null);
                    canvas.drawBitmap(plus, fX - plus.getWidth()/2,
                            fY - plus.getHeight()/2, null);
                }
                animateX = animateX + scaleX;
                animateY = animateY + scaleY;

                ourHolder.unlockCanvasAndPost(canvas);
            }


        }
    }
}
