package ch.heig.cashflow.adapters;

import android.content.Context;

import java.io.Serializable;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.CategoriesService;

@SuppressWarnings("serial")
public abstract class AddOrEditAdapter implements Serializable {
    protected Transaction transaction;
    protected CategoriesService.Callback callback;

    public AddOrEditAdapter(Transaction t){
        transaction = t;
    }

    public void setCallback(CategoriesService.Callback callback){
        this.callback = callback;
        loadCategories();
    }

    public abstract Transaction getTransaction();

    public abstract String getViewTitle(Context context);

    //public abstract void performAction(Transaction t);

    public abstract void loadCategories();
}
