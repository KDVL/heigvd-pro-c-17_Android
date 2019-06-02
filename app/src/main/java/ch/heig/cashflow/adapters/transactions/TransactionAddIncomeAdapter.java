/**
 * Adapter to add income
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddAdapter
 */

package ch.heig.cashflow.adapters.transactions;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Income;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.utils.Date;
import ch.heig.cashflow.utils.Type;

public class TransactionAddIncomeAdapter extends TransactionAddAdapter implements Serializable {

    /**
     * The TransactionAddIncomeAdapter constructor
     */
    public TransactionAddIncomeAdapter() {
        super(new Income(0, Date.getCurrentDateServerFormat(), null, 0, ""));
    }

    /**
     * Return the title specially adapted for adapter operation type income
     *
     * @param context the application context
     * @return String the view title
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
