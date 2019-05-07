package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;
import java.util.Calendar;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Income;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.utils.Date;

@SuppressWarnings("serial")
public class AddIncomeAdapter extends AddAdapter implements Serializable {

    public AddIncomeAdapter(){
        super(new Income(0,  Date.getCurrentDateServeurFormat(), null, 0, ""));
    }

    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_income_details);
    }

    @Override
    public void loadCategories() {
        new CategoriesService(callback).getType(Type.INCOME);
    }
}
