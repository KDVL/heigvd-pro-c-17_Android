/**
 * Adapter to add category type income
 *
 * @authors Aleksandar Milenkovic
 * @version 1.0
 * @see ch.heig.cashflow.adapters.categories.CategoryAddIncomeAdapter
 */
package ch.heig.cashflow.adapters.categories;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.utils.Type;

public class CategoryAddIncomeAdapter extends CategoryAddAdapter implements Serializable {

    /**
     * Constructor
     */
    public CategoryAddIncomeAdapter() {
        super(new Category(0, "", "", Type.INCOME, 0, true));
    }

    /**
     * Return the title specially adapted for adapter operation type income
     * @param context the context of application
     * @return the title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_cat_income_add);
    }
}
