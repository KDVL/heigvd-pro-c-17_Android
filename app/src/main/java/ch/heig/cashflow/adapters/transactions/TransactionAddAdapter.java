/**
 * Adapter to add parent abstract
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

public abstract class TransactionAddAdapter extends TransactionAddOrEditAdapter implements Serializable {

    /**
     * The TransactionAddAdapter constructor
     *
     * @param transaction The transaction
     */
    public TransactionAddAdapter(Transaction transaction) {
        super(transaction);
    }

    /**
     * Select spinner's category
     *
     * <p>
     * Select the first category by default
     *
     * @param categories The categories list
     * @param spinner    The spinner
     */
    public void selectCategory(List<Category> categories, Spinner spinner) {
        if (categories.size() > 0)
            spinner.setSelection(0);
    }

    /**
     * Do add
     */
    @Override
    public void performAction() {
        if (transactionCallback == null)
            return;
        new TransactionService(transactionCallback).add(transaction);
    }
}
