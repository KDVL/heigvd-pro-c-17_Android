/**
 * Adapter to edit, parent abstract
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters.categories;

import java.io.Serializable;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

public abstract class CategoryEditAdapter extends CategoryAddOrEditAdapter implements Serializable {

    /**
     * Constructor
     *
     * @param c the category
     */
    public CategoryEditAdapter(Category c) {
        super(c);
    }

    /**
     * do update
     */
    @Override
    public void performAction(CategoryService.Callback callback) {
        if (callback == null) return;
        (new CategoryService(callback)).update(category);
    }
}
