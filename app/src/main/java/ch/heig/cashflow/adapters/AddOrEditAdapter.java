package ch.heig.cashflow.adapters;

import android.content.Context;
import android.widget.Spinner;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.TransactionService;

@SuppressWarnings("serial")
public abstract class AddOrEditAdapter implements Serializable {

    protected Transaction transaction;
    protected CategoriesService.Callback callback;
    protected TransactionService.Callback transactionCallback;

    public AddOrEditAdapter(Transaction t){
        transaction = t;
    }

    public void setCallbackCategorie(CategoriesService.Callback callback){
        this.callback = callback;
        loadCategories();
    }

    public void setCallbackTransaction(TransactionService.Callback callback){
        this.transactionCallback = callback;
    }

    public Transaction getTransaction(){
        return transaction;
    }

    public String getAmount(){
        if(transaction.getAmountLong() == 0) return "";
        return String.valueOf(transaction.getAmountFloat());
    }

    public abstract String getViewTitle(Context context);

    public abstract void performAction();

    public abstract void loadCategories();

    public abstract void selectCategorie(List<Category> categories, Spinner s);
}
