/**
 * Adapter to add expense
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddAdapter
 */

package ch.heig.cashflow.adapters.transactions;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.utils.Date;
import ch.heig.cashflow.utils.Type;

public class TransactionAddExpenseAdapter extends TransactionAddAdapter implements Serializable {

    /**
     * The TransactionAddExpenseAdapter constructor
     */
    public TransactionAddExpenseAdapter() {
        super(new Expense(0, Date.getCurrentDateServerFormat(), null, 0, ""));
    }

    /**
     * Return the title specially adapted for adapter operation type income
     *
     * @param context The application context
     * @return String The view title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_expense_details);
    }

    /**
     * Call the service
     */
    @Override
    public void loadCategories() {
        new CategoriesService(callback).getType(Type.EXPENSE);
    }
}
