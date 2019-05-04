package ch.heig.cashflow.adapters;

import java.io.Serializable;

import ch.heig.cashflow.models.Transaction;

@SuppressWarnings("serial")
public abstract class EditAdapter extends AddOrEditAdapter implements Serializable {


    public EditAdapter(Transaction t){
        super(t);
    }

    public abstract Transaction getTransaction();
}
