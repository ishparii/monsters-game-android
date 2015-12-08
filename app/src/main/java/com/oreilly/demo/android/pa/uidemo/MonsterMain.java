package com.oreilly.demo.android.pa.uidemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.model.Monster;
import com.oreilly.demo.android.pa.uidemo.model.Monsters;
import com.oreilly.demo.android.pa.uidemo.view.MonsterGrid;

import java.util.concurrent.TimeUnit;

/**
 * Created by Group 03 on 12/1/15.
 */
public class MonsterMain extends Activity {

    /** finger target size*/
    public static DisplayMetrics displayMetrics = new DisplayMetrics();

    public static final int[] totalNumberProbArrays = {15, 20, 25, 30, 35};
    public static final int[] vulnerableProbArrays = {25, 20, 15, 10, 5};

    private boolean isStopped = true;

    /** Listen for taps. */
    private final class TrackingTouchListener implements View.OnTouchListener {

        private final Monsters mMonsters;
        private final MonsterGrid monsterGrid;

        TrackingTouchListener(Monsters mMonsters, MonsterGrid monsterGrid) {
            this.mMonsters = mMonsters;
            this.monsterGrid = monsterGrid;
        }

        @Override public boolean onTouch(View v, MotionEvent evt) {
            int action = evt.getAction();

            switch (action & MotionEvent.ACTION_MASK) {
                case MotionEvent.ACTION_DOWN:

                    float x = evt.getX();
                    float y = evt.getY();
                    x = x - monsterGrid.getLeftMargin();
                    y = y - monsterGrid.getTopMargin();

                    x = x / monsterGrid.getSquareHeight();
                    y = y / monsterGrid.getSquareWidth();

                    int indexX = (int)x;
                    int indexY = (int)y;

                    if(indexX >= 0 && indexX < monsterGrid.getRow() && indexY > 0 && indexY < monsterGrid.getColumn()
                            && mMonsters.positions[indexX][indexY]!=null && mMonsters.positions[indexX][indexY].isVulnerable()){

                        mMonsters.removeMonster(new Monster(indexX, indexY, monstersModel.getVulnerableProb()));
                        //point++;
                    // System.out.println("Touch!" + mMonsters.removeMonster(new Monster((int) x, (int) y, false)));
                    //Canvas canvas=new Canvas();MONSTERS_TOTAL
                       monsterGrid.invalidate();
                        pointView.setText(Integer.toString(mMonsters.killed));
                    }
                    break;
                default:
                    return false;
            }
            return true;
        }
    }

    /** The application model */
    final Monsters monstersModel = new Monsters(totalNumberProbArrays[0], vulnerableProbArrays[0]);

    /** The application view */
    MonsterGrid monsterGrid;

    private CountDownTimer timer;
    TextView textViewTimer;
    TextView pointView;
    Button buttonStart, buttonStop;

    private static final String FORMAT = "%02d";

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);

        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        setContentView(R.layout.monster_main);
        pointView= (TextView)findViewById(R.id.pointsView);

        this.setTitle(getResources().getText(R.string.app_name) + " - " + getResources().getText(R.string.menuLevel1));

        textViewTimer = (TextView) findViewById(R.id.clockView);
        buttonStart = (Button) findViewById(R.id.start);
        buttonStop = (Button) findViewById(R.id.stop);
        textViewTimer.setText("30");

        timer = new CountDownTimer(30000,1000){

            public void onTick(long millisUntilFinished){
                textViewTimer.setText(""+String.format(FORMAT, TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)));
            }
            public void onFinish(){
                monsterGrid.stopMoving();
                textViewTimer.setText("00");
                isStopped = true;
                buttonStop.setEnabled(false);
                buttonStart.setEnabled(true);

            }
        };

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isStopped){
                    timer.start();
                    monsterGrid.startMoving();
                    pointView.setText("0");
                    isStopped = false;
                    buttonStart.setEnabled(false);
                    buttonStop.setEnabled(true);
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isStopped){
                    monsterGrid.stopMoving();
                    pointView.setText("0");
                    timer.cancel();
                    textViewTimer.setText("30");
                    isStopped = true;
                    buttonStop.setEnabled(false);
                    buttonStart.setEnabled(true);
                }
            }
        });

        // find the monster view
        monsterGrid = (MonsterGrid) findViewById(R.id.monsterView);

        monstersModel.monsterGrid=this.monsterGrid;

        monsterGrid.setMonsters(monstersModel);

        monsterGrid.setOnCreateContextMenuListener(this);
        monsterGrid.setOnTouchListener(new TrackingTouchListener(monstersModel,monsterGrid));
    }

    /**
     * Install an options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.simple_menu, menu);
        return true;
    }

    /** Respond to an options menu selection. */
    @Override public boolean onOptionsItemSelected(MenuItem item) {
        this.setTitle(getResources().getText(R.string.app_name) + " - " + item.getTitle());
        isStopped = true;
        buttonStop.setEnabled(false);
        buttonStart.setEnabled(true);
        monsterGrid.stopMoving();
        pointView.setText("0");
        textViewTimer.setText("30");
        timer.cancel();

        switch (item.getItemId()) {
            case R.id.menuLevel1:
                monstersModel.setTotalNumberOfMonsters(totalNumberProbArrays[0]);
                monstersModel.setVulnerableProb(vulnerableProbArrays[0]);
                return true;
            case R.id.menuLevel2:
                monstersModel.setTotalNumberOfMonsters(totalNumberProbArrays[1]);
                monstersModel.setVulnerableProb(vulnerableProbArrays[1]);
                return true;
            case R.id.menuLevel3:
                monstersModel.setTotalNumberOfMonsters(totalNumberProbArrays[2]);
                monstersModel.setVulnerableProb(vulnerableProbArrays[2]);
                return true;
            case R.id.menuLevel4:
                monstersModel.setTotalNumberOfMonsters(totalNumberProbArrays[3]);
                monstersModel.setVulnerableProb(vulnerableProbArrays[3]);
                return true;
            case R.id.menuLevel5:
                monstersModel.setTotalNumberOfMonsters(totalNumberProbArrays[4]);
                monstersModel.setVulnerableProb(vulnerableProbArrays[4]);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
