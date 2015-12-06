package com.oreilly.demo.android.pa.uidemo.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;

import java.util.Observable;
import java.util.Random;
import java.util.ArrayList;

import com.oreilly.demo.android.pa.uidemo.R;

//<<<<<<< HEAD
import java.util.Observable;
import java.util.Random;
import java.util.ArrayList;
//=======

import java.util.Random;
//>>>>>>> b707bce7317ed6f344f333e483acf77d0a32ed2a

/** A monster: the coordinates and status */
public final class Monster extends Observable{
    private int x;
    private int y;
    private boolean isVulnerable;
    public boolean moved=false;
    private AsyncTask<Void,Void,Void> async=new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... params) {
            //params[0]=(Void)new Object();
            return null;
        }
    };
   // private Observable observable;
    //Random rand=new Random();



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

    public boolean isMoved(){
        return moved;
    }

    /** @return the status. */
    public boolean isVulnerable() {
        return isVulnerable;
    }

    //@Override
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

//<<<<<<< HEAD
    public boolean equals(Object obj){
        if(!(obj instanceof Monster))
           return false;
        if(obj==this)
            return true;
        if (  ((Monster)obj).getX()==getX() && ((Monster)obj).getY()==getY() && ((Monster)obj).isVulnerable()==isVulnerable())
             return true;
        else
             return false;
    }

   public Object[] move (Monster[][] positions,Random rand){
       Object[] result=new Object[4];
       int lx=positions.length;
       int ly=positions[0].length;
        //moved=true;
      //int startDirection;
      /* ArrayList<Integer> availableDirections=new ArrayList<>();
       for(int i=1;i<=8;i++){
           if (positions[Math.max(x - 1, 0)][y] == null)
               availableDirections.add(i);

       }*/
           int count=7;
           int direction = rand.nextInt(7);

          while(true){

              switch (direction) {
                  case 0:
                      if (positions[(x-1+lx)%lx][y] == null) {
                          result[0] = (x-1+lx)%lx;
                          result[1] = y;
                           moved=true;
                      }
                      break;
                  case 1:
                      if (positions[(x-1+lx)%lx][(y+1)%ly] == null) {
                          result[0] = (x-1+lx)%lx;
                          result[1] = (y+1)%ly;
                          moved=true;
                      }
                      break;
                  case 2:
                      if (positions[x][(y+1)%ly] == null) {
                          result[0] = x;
                          result[1] = (y+1)%ly;
                          moved=true;
                      }
                      break;
                  case 3:
                      if (positions[(x+1)%lx][(y+1)%ly] == null) {
                          result[0] = (x+1)%lx;
                          result[1] = (y+1)%ly;
                          moved=true;
                      }
                      break;
                  case 4:
                      if (positions[(x+1)%lx][y] == null) {
                          result[0] =(x+1)%lx;
                          result[1] = y;
                          moved=true;
                      }
                      break;
                  case 5:
                      if (positions[(x+1)%lx][(y-1+ly)%ly] == null) {
                          result[0] = (x+1)%lx;
                          result[1] = (y-1+ly)%ly;
                          moved=true;
                      }
                      break;
                  case 6:
                      if (positions[x][(y-1+ly)%ly] == null) {
                          result[0] = x;
                          result[1] = (y-1+ly)%ly;
                          moved=true;
                      }
                      break;
                  case 7:
                      if (positions[(x-1+lx)%lx][(y-1+ly)%ly] == null) {
                          result[0] = (x-1+lx)%lx;
                          result[1] = (y-1+ly)%ly;
                          moved=true;
                      }
                      break;
                  default:
                     // result[0] = x;
                      //result[1] = y;
                      //moved=false;
                      break;
              }

             direction= (direction+1) % 7;

             count--;
              if(count==-1 || moved)
                  break;
         }

       if(count==-1){//stay at the same place
         result[0] = x;
         result[1] = y;
           moved=true;
       }



       //result[0]=Math.max(x - 1, 0);
       //result[1]=Math.max(y- 1, 0);
       //result[0]=1;
       //result[1]=1;
       positions[x][y]=null;
       //positions[(int)result[0]][(int)result[1]]=this;
       result[2]=this.isVulnerable()?1:0;
       result[3]=positions;
       //positions[x][y]=null;
      setChanged();
       notifyObservers(result);




       return  result;

   }




//=======
    /*public void remove(Canvas canvas, int squareWidth, int leftMargin, int topMargin, Paint paint) {
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
    }*/
//>>>>>>> b707bce7317ed6f344f333e483acf77d0a32ed2a
}