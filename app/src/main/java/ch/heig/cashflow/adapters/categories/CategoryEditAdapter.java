/**
 * Adapter to edit category
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.CategoryAddOrEditAdapter
 */

package ch.heig.cashflow.adapters.categories;

import java.io.Serializable;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

public abstract class CategoryEditAdapter extends CategoryAddOrEditAdapter implements Serializable {

    /**
     * The CategoryEditAdapter constructor
     *
     * @param category the category
     */
    public CategoryEditAdapter(Category category) {
        super(category);
    }

    /**
     * Do edit a category
     *
     * @param callback The callback of service
     */
    @Override
    public void performAction(CategoryService.Callback callback) {
        if (callback == null)
            return;
        new CategoryService(callback).update(category);
    }
}
