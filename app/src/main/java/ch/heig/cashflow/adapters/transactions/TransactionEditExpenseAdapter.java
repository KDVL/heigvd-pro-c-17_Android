/**
 * Adapter to edit expense
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters.transactions;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.utils.Type;
import ch.heig.cashflow.network.services.CategoriesService;

public class TransactionEditExpenseAdapter extends TransactionEditAdapter implements Serializable {

    /**
     * Constructor
     * @param t the category
     */
    public TransactionEditExpenseAdapter(Transaction t) {
        super(t);
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
