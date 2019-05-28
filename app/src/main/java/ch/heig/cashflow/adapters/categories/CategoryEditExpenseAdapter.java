package ch.heig.cashflow.adapters.categories;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;

/**
 * Adapter to add category type expense
 *
 * @author Aleksandar Milenkovic
 * @version 1.0
 * @see ch.heig.cashflow.adapters.categories.CategoryEditExpenseAdapter
 */
public class CategoryEditExpenseAdapter extends CategoryEditAdapter implements Serializable {

    /**
     * Constructor
     *
     * @param c the category
     */
    public CategoryEditExpenseAdapter(Category c) {
        super(c);
    }

    /**
     * Return the title specially adapted for adapter operation type expense
     * @param context the context of application
     * @return the title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_cat_expense_details);
    }
}
