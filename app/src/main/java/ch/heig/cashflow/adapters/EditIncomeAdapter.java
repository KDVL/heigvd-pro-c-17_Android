package ch.heig.cashflow.adapters;

import java.io.Serializable;

import ch.heig.cashflow.models.Transaction;

@SuppressWarnings("serial")
public class EditIncomeAdapter extends EditAdapter implements Serializable {

    public EditIncomeAdapter(Transaction t) {
        super(t);
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }
}
