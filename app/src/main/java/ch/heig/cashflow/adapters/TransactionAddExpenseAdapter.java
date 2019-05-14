/**
 * Adapter to add expense
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.utils.Type;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.utils.Date;

public class TransactionAddExpenseAdapter extends TransactionAddAdapter implements Serializable {


    /**
     * Constructor
     */
    public TransactionAddExpenseAdapter() {
        super(new Expense(0, Date.getCurrentDateServeurFormat(), null, 0, ""));
    }

    /**
     *
     * @param context the context of application
     * @return the title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_expense_details);
    }

    /**
     * call the service
     */
    @Override
    public void loadCategories() {
        new CategoriesService(callback).getType(Type.EXPENSE);
    }
}
