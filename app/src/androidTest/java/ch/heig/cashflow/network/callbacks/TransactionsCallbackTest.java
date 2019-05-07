package ch.heig.cashflow.network.callbacks;

import android.support.test.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

public class TransactionsCallbackTest {
    TransactionsCallback tc;

    @Before
    public void setUp() throws Exception {
        tc = new TransactionsCallback(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @Test
    public void getAll() {
        tc.getAll();
    }

    @Test
    public void getAll1() {
    }

    @Test
    public void getType() {
    }

    @Test
    public void getType1() {
    }
}
