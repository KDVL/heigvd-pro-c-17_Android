package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Transaction;

@SuppressWarnings("serial")
public class AddIncomeAdapter extends AddAdapter implements Serializable {

    @Override
    public Transaction getTransaction() {
        return null;
    }

    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_income_details);
    }
}
