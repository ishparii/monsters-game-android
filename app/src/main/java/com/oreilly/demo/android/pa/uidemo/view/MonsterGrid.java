package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.model.Monster;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by Group 03 on 12/1/15.
 */
public class MonsterGrid extends View implements Observer{

    static final int FINGER_TARGET_SIZE_DP = 36;

    private int row;
    private int column;
    private int squareWidth;
    private int squareHeight;
    private int leftMargin;
    private int rightMargin;
    private int topMargin;
    private int bottomMargin;
    private int displayWidth;
    private int displayHeight;
    private Monsters monsters;
    private  Paint paint = new Paint();
    private boolean ifInit = false;

    /**
     * @param context the rest of the application
     */
    public MonsterGrid(Context context) {
        super(context);
        setFocusableInTouchMode(true);
    }

    /**
     * @param context
     * @param attrs
     */
    public MonsterGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusableInTouchMode(true);
    }

    /**
     * @param context
     * @param attrs
     * @param defStyle
     */
    public MonsterGrid(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setFocusableInTouchMode(true);
    }

    public void setMonsters(Monsters monsters){
        this.monsters = monsters;
    }

    public int getSquareWidth(){
        return squareWidth;
    }

    public int getSquareHeight(){
        return squareHeight;
    }

    public int getLeftMargin(){
        return leftMargin;
    }

    public int getTopMargin(){
        return topMargin;
    }

    @Override protected void onDraw(Canvas canvas) {
        if(!ifInit){
            initializeMeasures();
            ifInit = true;
        }

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        canvas.drawRect(leftMargin, topMargin, displayWidth - rightMargin, displayHeight
                - bottomMargin, paint);

        for(int i = 0 ; i < row - 1 ; i ++){
            canvas.drawLine(leftMargin, topMargin + (i+1) * squareWidth, displayWidth - rightMargin,
                    topMargin + (i+1) * squareWidth , paint);
        }

        for(int i = 0 ; i < column - 1 ; i ++){
            canvas.drawLine(leftMargin + (i+1) * squareWidth, topMargin , leftMargin + (i+1)
                    * squareWidth, displayHeight - bottomMargin, paint);
        }

        drawMonsters(canvas, paint);
    }

    public void initializeMeasures(){

        displayHeight = getHeight() - 10;
        displayWidth = getWidth() - 10;

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        squareWidth = Math.round(FINGER_TARGET_SIZE_DP * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        squareHeight = Math.round(FINGER_TARGET_SIZE_DP * (displayMetrics.ydpi / DisplayMetrics.DENSITY_DEFAULT));
        row = displayHeight / squareHeight;
        column = displayWidth / squareWidth;

        leftMargin = (displayWidth % squareWidth) / 2;
        rightMargin = (displayWidth % squareWidth) - (displayWidth % squareWidth) / 2;
        topMargin = (displayHeight % squareHeight) / 2;
        bottomMargin = (displayHeight % squareHeight) - (displayHeight % squareHeight) / 2;
    }

    private void drawMonsters(Canvas canvas, Paint paint){
        for(int i = 0 ; i < monsters.getMonsters().size() ; i++)
            monsters.getMonsters().get(i).draw(canvas, getContext(), squareWidth, leftMargin, topMargin, paint);
    }

    @Override
    public void update(Observable o, Object arg){  //observer pattern
        Monster m = (Monster)arg;
        Object[] params = new Object[2];
        params[0] = monsters.positions;
        params[1] = m;
        m.async.cancel(false);
        m.async = new Monster.Async();
        m.async.execute(params);
        invalidate();
    }

    public void stopMoving(){
        monsters.stopMoving();
        monsters.clearMonsters();
        invalidate();
    }

    public void startMoving(){
        monsters.initializeMonsters(column, row);
        monsters.startMoving();
    }
}
