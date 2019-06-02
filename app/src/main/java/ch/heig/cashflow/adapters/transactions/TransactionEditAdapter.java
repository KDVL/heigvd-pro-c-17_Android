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

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionService;

public abstract class TransactionEditAdapter extends TransactionAddOrEditAdapter implements Serializable {

    /**
     * The TransactionEditAdapter constructor
     *
     * @param transaction The transaction
     */
    public TransactionEditAdapter(Transaction transaction) {
        super(transaction);
    }

    /**
     * Select the right category
     *
     * @param categories The list of categories
     * @param spinner    The spinner
     */
    public void selectCategory(List<Category> categories, Spinner spinner) {
        Category category = transaction.getCategory();
        for (int i = 0; i < categories.size(); i++) {
            Category c = categories.get(i);
            if (category.getID() == c.getID()) {
                spinner.setSelection(i);
                return;
            }
        }
    }

    /**
     * Do update
     */
    @Override
    public void performAction() {
        if (transactionCallback == null)
            return;
        new TransactionService(transactionCallback).update(transaction);
    }
}
