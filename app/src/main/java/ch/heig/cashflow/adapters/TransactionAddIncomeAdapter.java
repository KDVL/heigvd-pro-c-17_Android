/**
 * Adapter to add income
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.TransactionAddOrEditAdapter
 *
 */
package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Income;
import ch.heig.cashflow.utils.Type;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.utils.Date;

public class TransactionAddIncomeAdapter extends TransactionAddAdapter implements Serializable {

    /**
     * Constructor
     */
    public TransactionAddIncomeAdapter() {
        super(new Income(0, Date.getCurrentDateServeurFormat(), null, 0, ""));
    }

    /**
     *
     * @param context the context of application
     * @return the title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_income_details);
    }

    /**
     * call the service
     */
    @Override
    public void loadCategories() {
        new CategoriesService(callback).getType(Type.INCOME);
    }
}
