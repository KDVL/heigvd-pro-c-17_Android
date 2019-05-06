package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import java.util.List;

import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.TransactionsService;

public class TransactionsCallback implements TransactionsService.Callback {

    Context context;

    TransactionsService ts;

    public TransactionsCallback(Context context) {
        this.context = context;
        this.ts = new TransactionsService(this);
    }

    public void getAll() {
        ts.getAll();
    }

    public void getAll(int year, int month) {
        ts.getAll(year, month);
    }

    public void getType(Type type) {
        ts.getType(type);
    }

    public void getType(Type type, int year, int month) {
        ts.getType(type, year, month);
    }

    @Override
    public void connectionFailed(String error) {
        System.out.println(error);
    }

    @Override
    public void getFinished(List<Transaction> transactions) {
        System.out.println(transactions);
    }

    @Override
    public Context getContext() {
        return context;
    }
}