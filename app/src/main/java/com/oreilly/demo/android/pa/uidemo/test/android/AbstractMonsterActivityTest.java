package com.oreilly.demo.android.pa.uidemo.test.android;

import android.widget.Button;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.MonsterMain;
import com.oreilly.demo.android.pa.uidemo.R;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

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
                assertEquals(30, getTimerValue());
                assertTrue(getStartButton().isEnabled());
                assertTrue(!getStopButton().isEnabled());

            }
        });
    }

    /**
     * Verifies the following scenario: time is 0, press button 5 times, wait 2.5 seconds, expect runtime value 5.
     *
     * @throws Throwable
     */

    @Test
    public void testActivityScenarioCountDown() throws Throwable {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                assertEquals(0, getPoint());
                assertEquals(30, getTimerValue());
                assertTrue(getStartButton().isEnabled());
                assertTrue(getStartButton().performClick());
            }
        });
        Thread.sleep(2000);
        runUiThreadTasks();
        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            assertEquals(28, getTimerValue());
        }});

        Thread.sleep(5000);
        runUiThreadTasks();
        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
            assertEquals(23, getTimerValue());
        }});
    }

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
    /**
     * Explicitly runs tasks scheduled to run on the UI thread in case this is required
     * by the testing framework, e.g., Robolectric.
     */
    protected void runUiThreadTasks() {

    }
}
