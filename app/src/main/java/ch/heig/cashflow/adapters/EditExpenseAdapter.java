package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Transaction;

@SuppressWarnings("serial")
public class EditExpenseAdapter extends EditAdapter implements Serializable {

    public EditExpenseAdapter(Transaction t) {
        super(t);
    }

    @Override
    public Transaction getTransaction() {
        return transaction;
    }

    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_expense_details);
    }
}
