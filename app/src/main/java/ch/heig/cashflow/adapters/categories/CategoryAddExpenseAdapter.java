/**
 * Adapter to add expense
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters.categories;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.utils.Type;

public class CategoryAddExpenseAdapter extends CategoryAddAdapter implements Serializable {


    /**
     * Constructor
     */
    public CategoryAddExpenseAdapter() {
        super(new Category(0, "", "", Type.EXPENSE, 0, true));
    }

    /**
     * @param context the context of application
     * @return the title
     */
    @Override
    public String getViewTitle(Context context) {
        return context.getString(R.string.title_cat_expense_add);
    }
}