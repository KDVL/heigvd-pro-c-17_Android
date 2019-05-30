/**
 * Adapter to edit category type income
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */

package ch.heig.cashflow.adapters.categories;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;

public class CategoryEditIncomeAdapter extends CategoryEditAdapter implements Serializable {

    /**
     * The CategoryEditIncomeAdapter constructor
     *
     * @param category the category
     */
    public CategoryEditIncomeAdapter(Category category) {
        super(category);
    }

    /**
     * Return the title specially adapted for adapter operation type income
     *
     * @param context The application context
     * @return String The title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_cat_income_details);
    }

}

