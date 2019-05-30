/**
 * The category abstract parent adapter
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 */

package ch.heig.cashflow.adapters.categories;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

public abstract class CategoryAddOrEditAdapter implements Serializable {

    protected Category category;

    /**
     * The CategoryAddOrEditAdapter constructor
     *
     * @param category The category
     */
    public CategoryAddOrEditAdapter(Category category) {
        this.category = category;
    }

    /**
     * Get the category
     *
     * @return Category The category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * Return quota as String
     *
     * @return String The quota
     */
    public String getQuota() {
        if (category.getQuota() == 0)
            return "";
        return String.valueOf(category.getQuota());
    }

    /**
     * Abstract methode who return the title specially adapted for adapter operation
     *
     * @param context The application context
     * @return String The title
     */
    public abstract String getViewTitle(Context context);

    /**
     * Abstract methode who add or edit a category
     *
     * @param callback The service
     */
    public abstract void performAction(CategoryService.Callback callback);

}
