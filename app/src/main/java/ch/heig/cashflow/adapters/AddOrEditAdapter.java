package ch.heig.cashflow.adapters;

import java.io.Serializable;

import ch.heig.cashflow.models.Transaction;

@SuppressWarnings("serial")
public abstract class AddOrEditAdapter implements Serializable {
    protected Transaction transaction;

    public AddOrEditAdapter(Transaction t){
        transaction = t;
    }

    public abstract Transaction getTransaction();

}
