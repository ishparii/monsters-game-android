package com.oreilly.demo.android.pa.uidemo.test.android;

import android.widget.Button;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.MonsterMain;
import com.oreilly.demo.android.pa.uidemo.R;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Team 05 on 12/8/15.
 */
public abstract class AbstractMonsterActivityTest {

    /**
     * Verifies that the activity under test can be launched.
     */
    @Test
    public void testActivityCheckTestCaseSetUpProperly() {
        assertNotNull("activity should be launched successfully", getActivity());
    }

    /**
     * Verifies the following scenario: time is 0.
     *
     * @throws Throwable
     */
    @Test
    public void testActivityScenarioInit() throws Throwable {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertEquals(0, getPoint());
            }
        });
    }

    @Test
    public void testActivityScenarioStart() throws Throwable{
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getStartButton().performClick();
            }
        });
        Thread.sleep(3000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertEquals(3, getTimerValue());
                assertEquals(0, getPoint());
            }
        });
    }

    @Test
    public void testActivityScenarioStop() throws Throwable{
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getStartButton().performClick();
            }
        });
        Thread.sleep(2000);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                getStopButton().performClick();
                assertEquals(0, getTimerValue());
                assertEquals(0, getPoint());
                assertEquals(0, getTimerValue());
                assertEquals(0, getPoint());
            }
        });
    }

    // auxiliary methods for easy access to UI widgets

    protected abstract MonsterMain getActivity();

    protected int tvToInt(final TextView t) {
        return Integer.parseInt(t.getText().toString().trim());
    }

    protected int getPoint() {
        final TextView ts = (TextView) getActivity().findViewById(R.id.pointsView);
        return  tvToInt(ts);
    }

    protected int getTimerValue() {
        final TextView ts = (TextView) getActivity().findViewById(R.id.clockView);
        return  tvToInt(ts);
    }

    protected Button getStartButton() {
        return (Button) getActivity().findViewById(R.id.start);
    }

    protected Button getStopButton() {
        return (Button) getActivity().findViewById(R.id.stop);
    }

}
