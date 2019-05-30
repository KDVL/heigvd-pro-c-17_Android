/**
 * Adapter to add category
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */

package ch.heig.cashflow.adapters.categories;

import java.io.Serializable;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

public abstract class CategoryAddAdapter extends CategoryAddOrEditAdapter implements Serializable {

    /**
     * The CategoryAddAdapter constructor
     *
     * @param category The category
     */
    public CategoryAddAdapter(Category category) {
        super(category);
    }

    /**
     * Do add a category
     *
     * @param callback The service callback
     */
    @Override
    public void performAction(CategoryService.Callback callback) {
        if (callback == null)
            return;
        new CategoryService(callback).add(category);
    }
}
