/**
 * Adapter to add parent abstract
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters;

import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

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
     * select spinner's categorie
     *
     * @param categories the list
     * @param s          the spinner
     */
    public void selectCategorie(List<Category> categories, Spinner s) { // TODO: voir utilité pas de spinner
        //select first categorie by default
        if (categories.size() > 0) {
            s.setSelection(0);
        }
    }

    /**
     * do add
     */
    @Override
    public void performAction() {
        if (csCallback == null) return;
        (new CategoryService(csCallback)).add(category);
    }
}
