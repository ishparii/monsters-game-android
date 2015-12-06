package com.oreilly.demo.android.pa.uidemo.model;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Group 03 on 12/4/15.
 */
public class Clock {

    public interface OnTickListener{
        void onTick();
    }

    Timer timer = new Timer();
    private OnTickListener onTickListener;

    public void setOnTickListener(OnTickListener onTickListener){
        this.onTickListener = onTickListener;
    }

    public void start() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                onTickListener.onTick();
            }
        },1000,1000);
    }

//    public void stop(){
//        timer.cancel();
//    }
}
