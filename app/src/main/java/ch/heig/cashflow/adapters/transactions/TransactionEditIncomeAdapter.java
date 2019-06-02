/**
 * Adapter to edit income
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionEditAdapter
 */

package ch.heig.cashflow.adapters.transactions;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.utils.Type;

public class TransactionEditIncomeAdapter extends TransactionEditAdapter implements Serializable {

    /**
     * the TransactionEditIncomeAdapter constructor
     *
     * @param transaction The transaction
     */
    public TransactionEditIncomeAdapter(Transaction transaction) {
        super(transaction);
    }

    /**
     * Return the title specially adapted for adapter operation type income
     *
     * @param context The application context
     * @return String The view title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_income_details);
    }

    /**
     * Call the service
     */
    @Override
    public void loadCategories() {
        new CategoriesService(callback).getType(Type.INCOME);
    }
}

