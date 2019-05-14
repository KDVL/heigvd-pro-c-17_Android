/**
 * Parent adapter (abstract)
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters;

import android.content.Context;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.CategoryService;

public abstract class CategoryAddOrEditAdapter implements Serializable {

    protected Category category;
    protected CategoryService.Callback csCallback;
    protected CategoriesService.Callback cssCallback;

    /**
     * Constructor
     *
     * @param c the category
     */
    public CategoryAddOrEditAdapter(Category c) {
        category = c;
    }

    /**
     * set callback categorie
     *
     * @param callback the callback
     */
    public void setCallbackCategorie(CategoriesService.Callback callback) {
        cssCallback = callback;
        loadCategories();
    }

    /**
     * set callback category
     *
     * @param callback the callback
     */
    public void setCallbackCategory(CategoryService.Callback callback) {
        csCallback = callback;
        loadCategory();
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
    public abstract void performAction();

    /**
     * call right service to load categories
     */
    public abstract void loadCategories();

    /**
     * call right service to load one category
     */
    public abstract void loadCategory();

    /**
     * select spinner's categorie
     *
     * @param categories the list
     * @param s          the spinner
     */
    public abstract void selectCategorie(List<Category> categories, Spinner s); // TODO: à voir utilité

}
