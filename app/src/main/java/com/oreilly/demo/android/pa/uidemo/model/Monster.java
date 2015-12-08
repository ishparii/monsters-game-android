package com.oreilly.demo.android.pa.uidemo.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.AsyncTask;

import com.oreilly.demo.android.pa.uidemo.R;

import java.util.Observable;
import java.util.Random;

/** A monster: the coordinates and status */
public final class Monster extends Observable{
    private int x;
    private int y;
    private int vulnerableProb;
    private boolean isVulnerable;
    public boolean moved=false;
    public int t=0;

    public static Random ra=new Random();

    public int getVulnerableProb() {
        return vulnerableProb;
    }

    public void setVulnerableProb(int vulnerableProb) {
        this.vulnerableProb = vulnerableProb;
    }

    public Async async=new Async();

    public static class Async extends AsyncTask<Object,Void,Monster> {
        @Override
        protected Monster doInBackground(Object... params) {

            Monster[][] positions = (Monster[][]) params[0];
            Monster m=(Monster) params[1];
            m.moved=false;

                try {

                      Thread.sleep(80); //move every 0.08 second

                } catch (InterruptedException e) {
                }


                 m.move(positions);

            return m;
        }

        @Override
        protected void onPostExecute(Monster m) {
            if(m.isMoved()) {
                m.setChanged();
                m.notifyObservers(m);//tell MonsterGrid monster has moved
            }
        }

    };

    /**
     * @param x horizontal coordinate at top left corner.
     * @param y vertical coordinate at top left corner.
     */
    public Monster(int x, int y, int vulnerableProb) {
        this.x = x;
        this.y = y;
        this.vulnerableProb = vulnerableProb;
        this.isVulnerable = ra.nextInt(100) < vulnerableProb;
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

    //draw monsters
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

    //define if two monsters equals
    public boolean equals(Object obj){
        if(!(obj instanceof Monster))
           return false;
        if(obj==this)
            return true;
        if (  ((Monster)obj).getX()==getX() && ((Monster)obj).getY()==getY())
             return true;
        else
             return false;
    }

   public synchronized Object[] move (Monster[][] positions){ // positions is a matrix


       Object[] result=new Object[4];
       int lx=positions.length; //lx is the number of rows of the positions
       int ly=positions[0].length; // ly is the number of columns of the positions
           int count=7;
           int direction = ra.nextInt(8);

          while(true){

              switch (direction) {
                  case 0:
                      if (positions[(x-1+lx)%lx][y] == null) { //the box above the monster
                          result[0] = (x-1+lx)%lx; //%lx, %ly to limit boundary
                          result[1] = y;
                           moved=true;
                      }
                      break;
                  case 1:
                      if (positions[(x-1+lx)%lx][(y+1)%ly] == null) {// top right
                          result[0] = (x-1+lx)%lx;
                          result[1] = (y+1)%ly;
                          moved=true;
                      }
                      break;
                  case 2:
                      if (positions[x][(y+1)%ly] == null) { //right
                          result[0] = x;
                          result[1] = (y+1)%ly;
                          moved=true;
                      }
                      break;
                  case 3:
                      if (positions[(x+1)%lx][(y+1)%ly] == null) { //bottom right
                          result[0] = (x+1)%lx;
                          result[1] = (y+1)%ly;
                          moved=true;
                      }
                      break;
                  case 4:
                      if (positions[(x+1)%lx][y] == null) { //bottom
                          result[0] =(x+1)%lx;
                          result[1] = y;
                          moved=true;
                      }
                      break;
                  case 5:
                      if (positions[(x+1)%lx][(y-1+ly)%ly] == null) { //bottom left
                          result[0] = (x+1)%lx;
                          result[1] = (y-1+ly)%ly;
                          moved=true;
                      }
                      break;
                  case 6:
                      if (positions[x][(y-1+ly)%ly] == null) { //left
                          result[0] = x;
                          result[1] = (y-1+ly)%ly;
                          moved=true;
                      }
                      break;
                  case 7:
                      if (positions[(x-1+lx)%lx][(y-1+ly)%ly] == null) { //top left
                          result[0] = (x-1+lx)%lx;
                          result[1] = (y-1+ly)%ly;
                          moved=true;
                      }
                      break;
                  default:
                      break;
              }

             direction= (direction+1) % 8;

             count--;
              if(count==-1 || moved)
                  break;
         }

       if(count==-1){//stay at the same place
         result[0] = x;
         result[1] = y;
       }


       positions[x][y]=null;//what does this mean? There is no monster at (x,y)
       //positions[(int)result[0]][(int)result[1]]=this;
       result[2]=ra.nextInt(100)<vulnerableProb; //some probability to change to vulnerable
       result[3]=this;




       x=(int)(result[0]); //change the monster's x coordinate
       y=(int)(result[1]); //change the monster's y coordinate
       isVulnerable=(boolean)result[2]; //30 percent probability to change to vulnerable like above
       positions[x][y]=this;


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
        int randomVulnerability = rand.nextInt(100);
        if (randomVulnerability <= 40) {
            isVulnerable = true;
        } else {
            isVulnerable = false;
        }
        draw(canvas, context, squareWidth, leftMargin, topMargin, paint);
    }*/
}