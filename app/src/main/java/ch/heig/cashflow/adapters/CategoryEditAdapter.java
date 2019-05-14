/**
 * Adapter to edit, parent abstract
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters;

import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

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
     * select the right
     *
     * @param categories list of categories
     * @param s          the spinner
     */
    public void selectCategorie(List<Category> categories, Spinner s) { // TODO: voir utilité pas de spinner
        for (int i = 0; i < categories.size(); i++) {
            Category c = categories.get(i);
            if (category.getID() == c.getID()) {
                s.setSelection(i);
                return;
            }
        }
    }

    /**
     * do update
     */
    @Override
    public void performAction() {
        if (csCallback == null) return;
        (new CategoryService(csCallback)).update(category);
    }
}
