package com.thelastrobmitch.newbostontutorial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;

/**
 * Created by Robert on 5/09/2017.
 */

public class MyBringBack extends View{

    Bitmap gBall;
    float changingY;
    Typeface font;


    public MyBringBack(Context context) {
        super(context);
        gBall = BitmapFactory.decodeResource(getResources(), R.drawable.tennis75);
        changingY = 0;
        font = Typeface.createFromAsset(context.getAssets(), "28 Days Later.ttf");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

        Paint textPaint = new Paint();
        textPaint.setARGB(75, 254, 10, 50);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(75);
        textPaint.setTypeface(font);
        canvas.drawText("Robert was here", canvas.getWidth()/2, 650, textPaint);

        canvas.drawBitmap(gBall, (canvas.getWidth())/2, changingY, null);
        if(changingY < canvas.getHeight()){
            changingY += 10;
        } else {
            changingY = 0;
        }
        Rect middleRect = new Rect();
        middleRect.set(0, 400, canvas.getWidth(), 550);
        Paint ourBlue = new Paint();
        ourBlue.setColor(Color.BLUE);
        canvas.drawRect(middleRect, ourBlue);
        invalidate();
    }
}
