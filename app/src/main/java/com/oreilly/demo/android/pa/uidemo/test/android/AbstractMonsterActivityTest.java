package com.oreilly.demo.android.pa.uidemo.test.android;

import android.widget.Button;
import android.widget.TextView;

import com.oreilly.demo.android.pa.uidemo.MonsterMain;
import com.oreilly.demo.android.pa.uidemo.R;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
            }
        });
    }

    /**
     * Verifies the following scenario: time is 0, press button 5 times, wait 2.5 seconds, expect runtime value 5.
     *
     * @throws Throwable
     */

//    @Test
//    public void testActivityScenarioInc() throws Throwable {
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                assertEquals(0, getDisplayedValue());
//                for (int i = 0; i < 5; i++) {
//                    assertTrue(getStartStopButton().performClick());
//                }
//            }
//        });
//        Thread.sleep(2500);
//        runUiThreadTasks();
//        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
//            assertEquals(5, getDisplayedValue());
//        }});
//    }
//
//
//    /**
//     * Verifies the following scenario: time is 0, press button 5 times, wait 2+ seconds,
//     * expect time 5, press button 4 more times, wait 1 seconds, expect time 9, wait 2+ seconds,
//     * expect time 9, wait 1 seconds, expect time 8, wait 6 seconds, expect time 2.
//     *
//     * @throws Throwable
//     */
//    @Test
//    public void testActivityScenarioRun() throws Throwable {
//        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
//            assertEquals(0, getDisplayedValue());
//            for (int i = 0; i < 5; i++) {
//                assertTrue(getStartStopButton().performClick());
//            }
//        }});
//        Thread.sleep(2500); // <-- do not run this in the UI thread!
//        runUiThreadTasks();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                assertEquals(5, getDisplayedValue());
//                for (int i = 0; i < 4; i++) {
//                    assertTrue(getStartStopButton().performClick());
//                }
//            }
//        });
//        Thread.sleep(1000); // <-- do not run this in the UI thread!
//        runUiThreadTasks();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                assertEquals(9, getDisplayedValue());
//            }
//        });
//        Thread.sleep(2500);
//        runUiThreadTasks();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                assertEquals(9, getDisplayedValue());
//            }
//        });
//        Thread.sleep(1000);
//        runUiThreadTasks();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                assertEquals(8, getDisplayedValue());
//            }
//        });
//        Thread.sleep(6000);
//        runUiThreadTasks();
//        getActivity().runOnUiThread(new Runnable() { @Override public void run() {
//            assertEquals(2, getDisplayedValue());
//        }});
//    }

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
    /**
     * Explicitly runs tasks scheduled to run on the UI thread in case this is required
     * by the testing framework, e.g., Robolectric.
     */
    protected void runUiThreadTasks() {

    }
}
