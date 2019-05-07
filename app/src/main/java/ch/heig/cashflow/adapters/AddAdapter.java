/**
 * Adapter to add parent abstract
 * @see ch.heig.cashflow.adapters.AddOrEditAdapter
 *
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */
package ch.heig.cashflow.adapters;

import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionService;

@SuppressWarnings("serial")
public abstract class AddAdapter extends AddOrEditAdapter implements Serializable {

    public AddAdapter(Transaction t) {
        super(t);
    }

    public void selectCategorie(List<Category> categories, Spinner s){
        //select first categorie by default
        if(categories.size() > 0){
            s.setSelection(0);
        }
    }

    @Override
    public void performAction() {
        if(transactionCallback == null)return;

        (new TransactionService(transactionCallback)).add(transaction);
    }
}
