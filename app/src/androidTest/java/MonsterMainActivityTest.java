import android.test.ActivityInstrumentationTestCase2;

import com.oreilly.demo.android.pa.uidemo.MonsterMain;
import com.oreilly.demo.android.pa.uidemo.test.android.AbstractMonsterActivityTest;

/**
 * Created by Team 05 on 12/8/15.
 */
public class MonsterMainActivityTest extends ActivityInstrumentationTestCase2<MonsterMain> {

    /**
     * Creates an {@link ActivityInstrumentationTestCase2} for the
     * {@link SkeletonActivity} activity.
     */
    public MonsterMainActivityTest() {
        super(MonsterMain.class);
        actualTest = new AbstractMonsterActivityTest() {
            @Override
            protected MonsterMain getActivity() {
                // return activity instance provided by instrumentation test
                return MonsterMainActivityTest.this.getActivity();
            }
        };
    }

    private AbstractMonsterActivityTest actualTest;

    public void testActivityCheckTestCaseSetUpProperly() {
        actualTest.testActivityCheckTestCaseSetUpProperly();
    }

    public void testActivityScenarioInit() throws Throwable {
        actualTest.testActivityScenarioInit();
    }

    public void testActivityScenarioInc() throws Throwable {
        actualTest.testActivityScenarioCountDown();
    }

}
