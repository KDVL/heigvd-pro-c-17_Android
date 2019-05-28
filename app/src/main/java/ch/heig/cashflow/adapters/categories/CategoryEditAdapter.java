package ch.heig.cashflow.adapters.categories;

import java.io.Serializable;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

/**
 * Adapter to edit category
 *
 * @author Aleksandar Milenkovic
 * @version 1.0
 * @see ch.heig.cashflow.adapters.categories.CategoryEditAdapter
 */
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
     * Do edit a category
     * @param callback callback of service
     */
    @Override
    public void performAction(CategoryService.Callback callback) {
        if (callback == null) return;
        (new CategoryService(callback)).update(category);
    }
}
