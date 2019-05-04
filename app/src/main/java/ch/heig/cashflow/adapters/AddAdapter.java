package ch.heig.cashflow.adapters;

import java.io.Serializable;

import ch.heig.cashflow.models.Transaction;

@SuppressWarnings("serial")
public abstract class AddAdapter extends AddOrEditAdapter implements Serializable {

    public AddAdapter() {
        super(null);
    }

    public abstract Transaction getTransaction();
}
