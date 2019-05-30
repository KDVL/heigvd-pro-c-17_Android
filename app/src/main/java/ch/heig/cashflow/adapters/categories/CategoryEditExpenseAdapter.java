/**
 * Adapter to add category type expense
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */

package ch.heig.cashflow.adapters.categories;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;

public class CategoryEditExpenseAdapter extends CategoryEditAdapter implements Serializable {

    /**
     * The CategoryEditExpenseAdapter constructor
     *
     * @param category the category
     */
    public CategoryEditExpenseAdapter(Category category) {
        super(category);
    }

    /**
     * Return the title specially adapted for adapter operation type expense
     *
     * @param context The application context
     * @return String The title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_cat_expense_details);
    }
}
