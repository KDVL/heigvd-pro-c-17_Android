/**
 * Adapter to add expense
 * @see ch.heig.cashflow.adapters.AddOrEditAdapter
 *
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */
package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.utils.Date;

@SuppressWarnings("serial")
public class AddExpenseAdapter extends AddAdapter implements Serializable {

    public AddExpenseAdapter(){
        super(new Expense(0, Date.getCurrentDateServeurFormat(), null, 0, ""));
    }

    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_expense_details);
    }

    @Override
    public void loadCategories() {
        new CategoriesService(callback).getType(Type.EXPENSE);
    }
}
