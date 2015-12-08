package com.oreilly.demo.android.pa.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.graphics.Canvas;
import android.widget.Toast;


import com.oreilly.demo.android.pa.uidemo.model.Monsters;
import com.oreilly.demo.android.pa.uidemo.view.MonsterGrid;
import com.oreilly.demo.android.pa.uidemo.model.Monster;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Group 03 on 12/1/15.
 */
public class MonsterMain extends Activity {

    /** finger target size*/
    static final int FINGER_TARGET_SIZE_DP = 36;
    public static DisplayMetrics displayMetrics = new DisplayMetrics();


    /** Listen for taps. */
    private final class TrackingTouchListener implements View.OnTouchListener {

        private final Monsters mMonsters;
        private final MonsterGrid monsterGrid;
        private List<Integer> tracks = new ArrayList<Integer>();


        TrackingTouchListener(Monsters mMonsters,MonsterGrid monsterGrid) {
            this.mMonsters = mMonsters;
            this.monsterGrid=monsterGrid;
        }

        @Override public boolean onTouch(View v, MotionEvent evt) {
            int n;
            int idx;
            int action = evt.getAction();
            //int point=0;


            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                   float x=evt.getX() ;
                    float y=evt.getY();
                    x=x-monsterGrid.leftMargin;
                    y=y-monsterGrid.topMargin;

                    x=x/monsterGrid.squareWidth;
                    y=y/monsterGrid.squareHeight;
                    //System.out.println("Touch!"+(int)x);
                    //System.out.println("Touch!"+(int)y);


                     //x=(int)x;
                     //y=(int)y;
                    if(mMonsters.positions[(int)x][(int)y]!=null && mMonsters.positions[(int)x][(int)y].isVulnerable()){
                        mMonsters.removeMonster(new Monster((int) x, (int) y, false));
                        //point++;
                    // System.out.println("Touch!" + mMonsters.removeMonster(new Monster((int) x, (int) y, false)));
                    //Canvas canvas=new Canvas();
                       monsterGrid.invalidate();
                       // pointView.setText(mMonsters.killed);
                        pointView.setText(Integer.toString(mMonsters.killed));
                    }
                    //monsterGrid.update();




                    break;


                case MotionEvent.ACTION_POINTER_DOWN:
                    idx = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    tracks.add(Integer.valueOf(evt.getPointerId(idx)));
                    break;
                case MotionEvent.ACTION_POINTER_UP:
                    idx = (action & MotionEvent.ACTION_POINTER_INDEX_MASK)
                            >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                    tracks.remove(Integer.valueOf(evt.getPointerId(idx)));
                    break;
                case MotionEvent.ACTION_MOVE:
                    n = evt.getHistorySize();
                    for (Integer i: tracks) {
                        idx = evt.findPointerIndex(i.intValue());
//                        for (int j = 0; j < n; j++) {
//                            addDot(
//                                    mDots,
//                                    evt.getHistoricalX(idx, j),
//                                    evt.getHistoricalY(idx, j),
//                                    evt.getHistoricalPressure(idx, j),
//                                    evt.getHistoricalSize(idx, j));
//                        }
                    }
                    break;
                default:
                    return false;
            }

//            for (Integer i: tracks) {
//                idx = evt.findPointerIndex(i.intValue());
//                addDot(
//                        mDots,
//                        evt.getX(idx),
//                        evt.getY(idx),
//                        evt.getPressure(idx),
//                        evt.getSize(idx));
//            }
            return true;
        }

        private void addMonster(int x, int y, boolean isVulnerable) {
            //mMonsters.addMonster(x, y, isVulnerable);
        }
    }

    /** Generate new monsters, one per second. */
    private final class MonsterGenerator implements Runnable {
        final Monsters monsters;
        final MonsterGrid view;
        final int color;

        private final Handler hdlr = new Handler();

        private final Runnable makeDots = new Runnable() {
           @Override public void run() {
//               makeDot(dots, view, color);
           }
        };

        private volatile boolean done;

        MonsterGenerator(Monsters monsters, MonsterGrid view, int color) {
            this.monsters = monsters;
            this.view = view;
            this.color = color;
        }

        public void done() {
            done = true;
        }

        @Override
        public void run() {
            while (!done) {
                hdlr.post(makeDots);
                try {
                    Thread.sleep(2000);
                }
                catch (InterruptedException e) {
                }
            }
        }
    }

    private final Random rand = new Random();

    /** The application model */
    final Monsters monstersModel = new Monsters();



//    final Clock clockModel = new Clock();

    /** The application view */
    MonsterGrid monsterGrid;

    TextView textViewTimer;
    TextView pointView;
    Button buttonStart, buttonStop;
    //adjust the format h:m:s
    private static final String FORMAT = "%02d:%02d:%02d";




    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        //        Clock clock=new Clock();
//        clock.setOnTickListener(monstersModel);
//        clock.start();

      getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);



        //install the countdown timer
        setContentView(R.layout.monster_main);
        pointView= (TextView)findViewById(R.id.pointsView);

        textViewTimer = (TextView) findViewById(R.id.clockView);
        buttonStart = (Button) findViewById(R.id.start);
        buttonStop = (Button) findViewById(R.id.stop);
        textViewTimer.setText("00:01:00");


        final CountDownTimer timer = new CountDownTimer(60000,1000){

            public void onTick(long millisUntilFinished){
                textViewTimer.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toHours(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(
                                TimeUnit.MILLISECONDS.toHours(millisUntilFinished)),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                                - TimeUnit.MINUTES.toSeconds(
                                TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                ));
            }

            public void onFinish(){
                textViewTimer.setText("Time is Up!");
            }
        };

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    timer.start();

            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();
            }
        });






        // find the monster view
        monsterGrid = (MonsterGrid) findViewById(R.id.monsterView);
        //clock.setOnTickListener(monsterGrid);


        monstersModel.monsterGrid=this.monsterGrid;

        //monsterGrid.initializeMeasures();

        monsterGrid.setMonsters(monstersModel);

        monsterGrid.setOnCreateContextMenuListener(this);
        monsterGrid.setOnTouchListener(new TrackingTouchListener(monstersModel,monsterGrid));


        monsterGrid.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (KeyEvent.ACTION_DOWN != event.getAction()) {
//                    return false;
//                }
//
//                int color;
//                switch (keyCode) {
//                    case KeyEvent.KEYCODE_SPACE:
//                        color = Color.MAGENTA;
//                        break;
//                    case KeyEvent.KEYCODE_ENTER:
//                        color = Color.BLUE;
//                        break;
//                    default:
//                        return false;
//                }
//
//                makeDot(dotModel, dotView, color);

                return true;
            }
        });


        monsterGrid.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
//                if (!hasFocus && (null != dotGenerator)) {
//                    dotGenerator.done();
//                    dotGenerator = null;
//                }
//                else if (hasFocus && (null == dotGenerator)) {
//                    dotGenerator
//                            = new DotGenerator(dotModel, dotView, Color.BLACK);
//                    new Thread(dotGenerator).start();
//                }
            }
        });



    }


    /** Install an options menu. */
   /* @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }*/

    /** Respond to an options menu selection. */
//    @Override public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_restart:
//                clockModel.start();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    /** Install a context menu. */
//    @Override public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//        menu.add(Menu.NONE, 1, Menu.NONE, "Restart").setAlphabeticShortcut('r');
//    }

    /** Respond to a context menu selection. */
//    @Override public boolean onContextItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case 1:
//                new Thread();
//                clockModel.start();
//
//
//               return true;
//            default: ;
//       }
//        return false;
//    }

//    /**
//     * @param dots the dots we're drawing
//     * @param view the view in which we're drawing dots
//     * @param color the color of the dot
//     */

    void makeMonster(Monsters monsters, MonsterGrid monsterGrid) {


//        int pad = (DOT_DIAMETER + 2) * 2;
//        dots.addDot(
//                DOT_DIAMETER + (rand.nextFloat() * (view.getWidth() - pad)),
//                DOT_DIAMETER + (rand.nextFloat() * (view.getHeight() - pad)),
//                color,
//                DOT_DIAMETER);
    }

}
