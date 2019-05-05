package ch.heig.cashflow.network.services;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import ch.heig.cashflow.models.Transaction;

import static org.junit.Assert.assertNotNull;

public class TransactionsServiceTest {

    TransactionsService ts;

    @Before
    public void initialize() {
        ts = new TransactionsService(TransactionsServiceCallback);
    }

    @Test
    public void testGetAll() {
        ts.getAll();
    }

    public void testGetAll1() {
    }

    public void testGetType() {
    }

    public void testGetType1() {
    }

    public void testUpdateFromDownload() {
    }

    class TransactionsServiceCallback implements TransactionsService.Callback {

        @Override
        public Context getContext() {
            return null;
        }

        @Override
        public void connectionFailed(String error) {
            assertNotNull(error);
        }

        @Override
        public void getFinished(List<Transaction> transactions) {
            assertNotNull(transactions);
        }
    }

}