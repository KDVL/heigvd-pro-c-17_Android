package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import java.util.List;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.TransactionsService;

public class TransactionsCallback implements TransactionsService.Callback {

    Context context;
    ServicesFragment fragment;
    TransactionsService ts;

    public TransactionsCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
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
        //fragment.callDone = true;
        //fragment.setTransactionsServiceState(false);
        System.err.println("DOWN  - TransactionsService " + error);
    }

    @Override
    public void getFinished(List<Transaction> transactions) {
        //fragment.callDone = true;
        if (transactions.size() < 1)
            //fragment.setTransactionsServiceState(true);
            System.err.println("EMPTY LIST - TransactionsService");
        else
            System.out.println("OK    TransactionsService found " + transactions);
    }

    @Override
    public Context getContext() {
        return context;
    }
}