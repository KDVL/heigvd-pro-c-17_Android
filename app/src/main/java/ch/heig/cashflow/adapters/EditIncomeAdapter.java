/**
 * Adapter to edit income
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.AddOrEditAdapter
 */
package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.CategoriesService;

public class EditIncomeAdapter extends EditAdapter implements Serializable {

    /**
     * Constructor
     * @param t the transaction
     */
    public EditIncomeAdapter(Transaction t) {
        super(t);
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

