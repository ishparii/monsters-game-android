package com.oreilly.demo.android.pa.uidemo.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;

import com.oreilly.demo.android.pa.uidemo.R;

import java.util.Random;

/** A monster: the coordinates and status */
public final class Monster extends AsyncTask {
    private int x;
    private int y;
    private boolean isVulnerable;

    /**
     * @param x horizontal coordinate at top left corner.
     * @param y vertical coordinate at top left corner.
     * @param isVulnerable status of monster.
     */
    public Monster(int x, int y, boolean isVulnerable) {
        this.x = x;
        this.y = y;
        this.isVulnerable = isVulnerable;
    }

    /** @return the horizontal coordinate. */
    public int getX() {
        return x;
    }

    /** @return the vertical coordinate. */
    public int getY() {
        return y;
    }

    /** @return the status. */
    public boolean isVulnerable() {
        return isVulnerable;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }

    public void draw(Canvas canvas, Context context, int squareWidth, int leftMargin, int topMargin, Paint paint ){
        Bitmap image;
        if(isVulnerable()){
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.orange_monster);
        } else{
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_monster);
        }
        Bitmap imageScaled = Bitmap.createScaledBitmap(image,squareWidth,squareWidth, false);
        canvas.drawBitmap(imageScaled, getX()*squareWidth + leftMargin, getY()*squareWidth + topMargin, paint);

    }

    public void remove(Canvas canvas, int squareWidth, int leftMargin, int topMargin, Paint paint) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(getX()*squareWidth + leftMargin, getY()*squareWidth + topMargin, squareWidth, squareWidth, paint);
    }

    public void move(Canvas canvas, Context context, int squareWidth, int leftMargin, int topMargin, Paint paint) {
        remove(canvas, squareWidth, leftMargin, topMargin, paint);
        Random rand = new Random();
        x = getX() + (rand.nextInt(3)-1);
        y = getY() + (rand.nextInt(3)-1);
        draw(canvas, context, squareWidth, leftMargin, topMargin, paint);
    }
}