package com.oreilly.demo.android.pa.uidemo.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.oreilly.demo.android.pa.uidemo.R;

import java.util.Observable;
import java.util.Random;
import java.util.ArrayList;

/** A monster: the coordinates and status */
public final class Monster extends Observable {
    private final int x, y;
    private boolean isVulnerable;
    public boolean moved=false;
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
    public boolean getStatus() {
        return isVulnerable;
    }

    public void draw(Canvas canvas, Context context, int squareWidth, int leftMargin, int topMargin, Paint paint ){
        Bitmap image;
        if(getStatus()){
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.orange_monster);
        } else{
            image = BitmapFactory.decodeResource(context.getResources(), R.drawable.green_monster);
        }
        Bitmap imageScaled = Bitmap.createScaledBitmap(image,squareWidth,squareWidth, false);
        canvas.drawBitmap(imageScaled, getX()*squareWidth + leftMargin, getY()*squareWidth + topMargin, paint);

    }

    public boolean equals(Object obj){
        if(!(obj instanceof Monster))
           return false;
        if(obj==this)
            return true;
        if (  ((Monster)obj).getX()==getX() && ((Monster)obj).getY()==getY() && ((Monster)obj).getStatus()==getStatus())
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
                      if (positions[Math.max(x - 1, 0)][y] == null) {
                          result[0] = Math.max(x - 1, 0);
                          result[1] = y;
                           moved=true;
                      }
                      break;
                  case 1:
                      if (positions[Math.max(x - 1, 0)][Math.min(y + 1, ly - 1)] == null) {
                          result[0] = Math.max(x - 1, lx - 1);
                          result[1] = Math.min(y + 1, ly - 1);
                          moved=true;
                      }
                      break;
                  case 2:
                      if (positions[x][Math.min(y + 1, ly - 1)] == null) {
                          result[0] = x;
                          result[1] = Math.min(y + 1, ly - 1);
                          moved=true;
                      }
                      break;
                  case 3:
                      if (positions[Math.min(x + 1, lx - 1)][Math.min(y + 1, ly - 1)] == null) {
                          result[0] = Math.min(x + 1, lx - 1);
                          result[1] = Math.min(y + 1, ly - 1);
                          moved=true;
                      }
                      break;
                  case 4:
                      if (positions[Math.min(x + 1, lx - 1)][y] == null) {
                          result[0] = Math.min(x + 1, lx - 1);
                          result[1] = y;
                          moved=true;
                      }
                      break;
                  case 5:
                      if (positions[Math.min(x + 1, lx - 1)][Math.max(y - 1, 0)] == null) {
                          result[0] = Math.min(x + 1, lx - 1);
                          result[1] = Math.max(y - 1, 0);
                          moved=true;
                      }
                      break;
                  case 6:
                      if (positions[x][Math.max(y - 1, 0)] == null) {
                          result[0] = x;
                          result[1] = Math.max(y - 1, 0);
                          moved=true;
                      }
                      break;
                  case 7:
                      if (positions[Math.max(x - 1, 0)][Math.max(y - 1, 0)] == null) {
                          result[0] = Math.max(x - 1, 0);
                          result[1] = Math.max(y - 1, 0);
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
              if(count==0 || moved)
                  break;
         }

       if(count==0){//stay at the same place
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
       result[2]=this.getStatus()?1:0;
       result[3]=positions;
       //positions[x][y]=null;

      // notifyObservers(result);




       return  result;

   }


}