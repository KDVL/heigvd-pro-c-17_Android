/**
 * Adapter to edit income
 * @see ch.heig.cashflow.adapters.AddOrEditAdapter
 *
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */
package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.CategoriesService;

@SuppressWarnings("serial")
public class EditIncomeAdapter extends EditAdapter implements Serializable {

    public EditIncomeAdapter(Transaction t) {
        super(t);
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

