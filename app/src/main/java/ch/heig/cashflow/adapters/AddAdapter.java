/**
 * Adapter to add parent abstract
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.AddOrEditAdapter
 */
package ch.heig.cashflow.adapters;

import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionService;

public abstract class AddAdapter extends AddOrEditAdapter implements Serializable {

    /**
     * Constructor
     * @param t the transaction
     */
    public AddAdapter(Transaction t) {
        super(t);
    }

    /**
     * select spinner's categorie
     * @param categories the list
     * @param s the spinner
     */
    public void selectCategorie(List<Category> categories, Spinner s) {
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
        if (transactionCallback == null) return;

        (new TransactionService(transactionCallback)).add(transaction);
    }
}
