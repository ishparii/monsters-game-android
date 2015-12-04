package com.oreilly.demo.android.pa.uidemo.model.Clock;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by annezhao on 12/4/15.
 */
public class ConcreteClock implements ClockModel{

    Timer timer = new Timer();
    OnTickListener onTickListener;

    @Override
    public void setOnTickListener(OnTickListener onTickListener){
        this.onTickListener = onTickListener;
    }



    @Override
    public void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onTickListener.onTick();
            }
        },1000,1000);
    }

    @Override
    public void stop(){
        timer.cancel();
    }
}
