/**
 * Adapter to add parent abstract
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters.categories;

import java.io.Serializable;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

public abstract class CategoryAddAdapter extends CategoryAddOrEditAdapter implements Serializable {

    /**
     * Constructor
     *
     * @param c the category
     */
    public CategoryAddAdapter(Category c) {
        super(c);
    }

    /**
     * do add
     */
    @Override
    public void performAction(CategoryService.Callback callback) {
        if (callback == null) return;
        (new CategoryService(callback)).add(category);
    }
}
