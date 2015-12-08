package com.oreilly.demo.android.pa.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.model.Monster;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;
import com.oreilly.demo.android.pa.uidemo.view.MonsterGrid;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Group 03 on 12/1/15.
 */
public class MonsterMain extends Activity {

    /** finger target size*/
    public static DisplayMetrics displayMetrics = new DisplayMetrics();

    /** Listen for taps. */
    private final class TrackingTouchListener implements View.OnTouchListener {

        private final Monsters mMonsters;
        private final MonsterGrid monsterGrid;

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
                        mMonsters.removeMonster(new Monster((int) x, (int) y, monstersModel.getVulnerableProb()));
                        //point++;
                    // System.out.println("Touch!" + mMonsters.removeMonster(new Monster((int) x, (int) y, false)));
                    //Canvas canvas=new Canvas();MONSTERS_TOTAL
                       monsterGrid.invalidate();
                       // pointView.setText(mMonsters.killed);
                        pointView.setText(Integer.toString(mMonsters.killed));
                    }
                    //monsterGrid.update();
                    break;
                default:
                    return false;
            }
            return true;
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
    private static final String FORMAT = "%02d";




    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        //install the countdown timer
        setContentView(R.layout.monster_main);
        pointView= (TextView)findViewById(R.id.pointsView);

        textViewTimer = (TextView) findViewById(R.id.clockView);
        buttonStart = (Button) findViewById(R.id.start);
        buttonStop = (Button) findViewById(R.id.stop);
        textViewTimer.setText("30");


        final CountDownTimer timer = new CountDownTimer(30000,1000){

            public void onTick(long millisUntilFinished){
                textViewTimer.setText(""+String.format(FORMAT,
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                ));
            }
            public void onFinish(){
                monsterGrid.stopMoving();
                textViewTimer.setText("00");
            }
        };

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                monsterGrid.startMoving();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                monsterGrid.stopMoving();
                pointView.setText("0");
                timer.cancel();
            }
        });

        // find the monster view
        monsterGrid = (MonsterGrid) findViewById(R.id.monsterView);
        //clock.setOnTickListener(monsterGrid);

        monstersModel.monsterGrid=this.monsterGrid;

        monsterGrid.setMonsters(monstersModel);

        monsterGrid.setOnCreateContextMenuListener(this);
        monsterGrid.setOnTouchListener(new TrackingTouchListener(monstersModel,monsterGrid));
    }
}
