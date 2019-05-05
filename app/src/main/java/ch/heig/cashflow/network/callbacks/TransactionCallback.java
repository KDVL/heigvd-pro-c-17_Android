package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionService;

public class TransactionCallback implements TransactionService.Callback {

    Context context;
    ServicesFragment fragment;
    TransactionService ts;

    public TransactionCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.ts = new TransactionService(this);
    }

    public void get(long id) {
        ts.get(id);
    }

    public void add(Transaction transaction) {
        ts.add(transaction);
    }

    public void update(Transaction transaction) {
        ts.update(transaction);
    }

    public void delete(Transaction transaction) {
        ts.delete(transaction);
    }

    @Override
    public void connectionFailed(String error) {
        System.err.println("DOWN  - TransactionService " + error);
    }

    @Override
    public void operationFinished(boolean isFinished) {
        System.out.println("OK    TransactionService found");
    }

    @Override
    public void getFinished(Transaction transaction) {
        if (transaction == null)
            System.err.println("EMPTY - TransactionService");
        else
            System.out.println("OK    TransactionService found " + transaction);
    }

    @Override
    public Context getContext() {
        return context;
    }
}