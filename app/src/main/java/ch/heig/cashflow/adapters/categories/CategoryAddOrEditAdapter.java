/**
 * Parent adapter (abstract)
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters.categories;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

public abstract class CategoryAddOrEditAdapter implements Serializable {

    protected Category category;

    /**
     * Constructor
     *
     * @param c the category
     */
    public CategoryAddOrEditAdapter(Category c) {
        category = c;
    }

    /**
     * @return category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * return amount as String
     *
     * @return String amount
     */
    public String getAmount() {
        if (category.getQuota() == 0) return "";
        return String.valueOf(category.getQuota());
    }

    /**
     * Set title view
     */
    public abstract String getViewTitle(Context context);

    /**
     * Add or edit
     */
    public abstract void performAction(CategoryService.Callback callback);

}
