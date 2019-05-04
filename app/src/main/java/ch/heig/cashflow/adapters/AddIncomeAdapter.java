package ch.heig.cashflow.adapters;

import java.io.Serializable;

import ch.heig.cashflow.models.Transaction;

@SuppressWarnings("serial")
public class AddIncomeAdapter extends AddAdapter implements Serializable {

    @Override
    public Transaction getTransaction() {
        return null;
    }
}
