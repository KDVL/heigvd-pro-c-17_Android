package ch.heig.cashflow.adapters;

import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;

@SuppressWarnings("serial")
public abstract class AddAdapter extends AddOrEditAdapter implements Serializable {

    public AddAdapter() {
        super(null);
    }

    public abstract Transaction getTransaction();

    public void selectCategorie(List<Category> categories, Spinner s){
        //nothing to do, select first categorie by default
    }
}
