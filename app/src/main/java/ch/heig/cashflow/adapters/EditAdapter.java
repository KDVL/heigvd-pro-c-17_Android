package ch.heig.cashflow.adapters;

import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;

@SuppressWarnings("serial")
public abstract class EditAdapter extends AddOrEditAdapter implements Serializable {


    public EditAdapter(Transaction t){
        super(t);
    }

    public abstract Transaction getTransaction();


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
}
