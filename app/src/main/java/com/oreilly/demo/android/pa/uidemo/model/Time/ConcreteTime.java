package com.oreilly.demo.android.pa.uidemo.model.Time;

/**
 * Created by Group 03 on 12/4/15.
 */
public class ConcreteTime implements TimeModel {

    int overAllTime = 60;
    final int SEC_PER_TICK = 1;

    @Override
    public void resetTime() {
        overAllTime = 60;
    }

    @Override
    public void countDown() {
        overAllTime = overAllTime - SEC_PER_TICK;
    }
}
