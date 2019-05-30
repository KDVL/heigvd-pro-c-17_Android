/**
 * Adapter to edit, parent abstract
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters.transactions;

import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionService;

public abstract class TransactionEditAdapter extends TransactionAddOrEditAdapter implements Serializable {

    /**
     * Constructor
     * @param t the category
     */
    public TransactionEditAdapter(Transaction t) {
        super(t);
    }

    /**
     * select the right
     * @param categories list of categories
     * @param s the spinner
     */
    public void selectCategorie(List<Category> categories, Spinner s) {
        Category category = transaction.getCategory();

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
        if (transactionCallback == null) return;
        (new TransactionService(transactionCallback)).update(transaction);
    }
}
