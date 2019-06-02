/**
 * Adapter to add category type expense
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.CategoryAddAdapter
 */

package ch.heig.cashflow.adapters.categories;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.utils.Type;

public class CategoryAddExpenseAdapter extends CategoryAddAdapter implements Serializable {

    /**
     * The CategoryAddExpenseAdapter constructor
     */
    public CategoryAddExpenseAdapter() {
        super(new Category(0, "", "", Type.EXPENSE, 0, true));
    }

    /**
     * Return the title specially adapted for adapter operation type expense
     *
     * @param context The application context
     * @return String The view title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_cat_expense_add);
    }
}
