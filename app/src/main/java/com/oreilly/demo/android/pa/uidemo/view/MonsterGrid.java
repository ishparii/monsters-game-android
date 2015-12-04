package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.oreilly.demo.android.pa.uidemo.R;
import com.oreilly.demo.android.pa.uidemo.model.Monster;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;

import java.util.Random;

/**
 * Created by Group 03 on 12/1/15.
 */
public class MonsterGrid extends View {

    static final int FINGER_TARGET_SIZE_DP = 36;

    private int row;
    private int column;
    private int squareWidth;
    private int leftMargin;
    private int rightMargin;
    private int topMargin;
    private int bottomMargin;
    private int displayWidth;
    private int displayHeight;
    private Monsters monsters;
    private int[][] positions;

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

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    /**
     * @see android.view.View#onDraw(android.graphics.Canvas)
     */
    @Override protected void onDraw(Canvas canvas) {
        initializeMeasures();
        Paint paint = new Paint();
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

        initializeMonsters(canvas,paint);
    }

    private void initializeMeasures(){

        displayHeight = getHeight() - 10;
        displayWidth = getWidth() - 10;

        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        squareWidth = Math.round(FINGER_TARGET_SIZE_DP * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));

        row = displayHeight / squareWidth;
        column = displayWidth / squareWidth;

        leftMargin = (displayWidth % squareWidth) / 2;
        rightMargin = (displayWidth % squareWidth) - (displayWidth % squareWidth) / 2;
        topMargin = (displayHeight % squareWidth) / 2;
        bottomMargin = (displayHeight % squareWidth) - (displayHeight % squareWidth) / 2;

        positions = new int[column][row];

        for(int i = 0 ; i < column ; i++)
            for(int j = 0 ; j < row ; j++)
                positions[i][j] = 0;
    }

    private void initializeMonsters(Canvas canvas, Paint paint){

        Random rand = new Random();
        for(int i = 0 ; i < 10 ; i++){
            boolean exist = true;
            while (exist){
                int x = rand.nextInt(column);
                int y = rand.nextInt(row);
                if(positions[x][y]==0){
                    exist = false;
                    positions[x][y]=1;
                    this.monsters.addMonster(x, y, i%2==0);
                    monsters.getLastMonster().draw(canvas,  getContext(), squareWidth, leftMargin, topMargin, paint);
                }
            }
        }
    }
}
