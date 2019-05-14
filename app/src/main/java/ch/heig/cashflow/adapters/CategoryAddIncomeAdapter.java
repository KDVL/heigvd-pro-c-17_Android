/**
 * Adapter to add income
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.services.CategoriesService;

public class CategoryAddIncomeAdapter extends CategoryAddAdapter implements Serializable {

    /**
     * Constructor
     */
    public CategoryAddIncomeAdapter() {
        super(new Category(0, "", "", Type.INCOME, 0, true));
    }

    /**
     * @param context the context of application
     * @return the title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_cat_income_add);
    }

    /**
     * call the service
     */
    @Override
    public void loadCategories() {
        new CategoriesService(cssCallback).getAll();
    }

    @Override
    public void loadCategory() {

    }
}
