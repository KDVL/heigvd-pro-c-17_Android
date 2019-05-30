package ch.heig.cashflow.adapters.categories;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

/**
 * Parent adapter (abstract)
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.adapters.categories.CategoryAddOrEditAdapter
 */
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
     * return quota as String
     *
     * @return String quota
     */
    public String getQuota() {
        if (category.getQuota() == 0) return "";
        return String.valueOf(category.getQuota());
    }

    /**
     * abstract methode who
     * return the title specially adapted for adapter operation
     *
     * @param context the context of application
     * @return the title
     */
    public abstract String getViewTitle(Context context);

    /**
     * abstract methode who
     * add or edit a category
     *
     * @param callback the service
     */
    public abstract void performAction(CategoryService.Callback callback);

}
