package ch.heig.cashflow.adapters;

import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionService;

@SuppressWarnings("serial")
public abstract class EditAdapter extends AddOrEditAdapter implements Serializable {


    public EditAdapter(Transaction t){
        super(t);
    }

    //TODO : Test with correct categories
    public void selectCategorie(List<Category> categories, Spinner s){
        Category category = transaction.getCategory();
        for(int i = 0; i < categories.size(); i++){
            Category c = categories.get(i);
            if(category.equals(c)){
                s.setSelection(i);
                return;
            }
        }
    }

    @Override
    public void performAction() {
        if(transactionCallback == null) return;

        (new TransactionService(transactionCallback)).update(transaction);
    }
}
