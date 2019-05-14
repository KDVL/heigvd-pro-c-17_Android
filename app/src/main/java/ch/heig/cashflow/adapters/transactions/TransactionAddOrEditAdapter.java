/**
 * Parent adapter (abstract)
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.adapters.transactions.TransactionAddOrEditAdapter
 */
package ch.heig.cashflow.adapters.transactions;

import android.content.Context;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.TransactionService;

public abstract class TransactionAddOrEditAdapter implements Serializable {

    protected Transaction transaction;
    protected CategoriesService.Callback callback;
    protected TransactionService.Callback transactionCallback;

    /**
     * Constructor
     * @param t the category
     */
    public TransactionAddOrEditAdapter(Transaction t) {
        transaction = t;
    }

    /**
     *  set callback categorie
     * @param callback the callback
     */
    public void setCallbackCategorie(CategoriesService.Callback callback) {
        this.callback = callback;
        loadCategories();
    }

    /**
     * set callback category
     * @param callback the callback
     */
    public void setCallbackTransaction(TransactionService.Callback callback) {
        this.transactionCallback = callback;
    }

    /**
     *
     * @return category
     */
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * return amount as String
     * @return String amount
     */
    public String getAmount() {
        if (transaction.getAmountLong() == 0) return "";
        return String.valueOf(transaction.getAmountFloat());
    }

    /**
     * Set title view
     */
    public abstract String getViewTitle(Context context);

    /**
     * Add or edit
     */
    public abstract void performAction();

    /**
     * call right service to load categories
     */
    public abstract void loadCategories();

    /**
     * select spinner's categorie
     * @param categories the list
     * @param s the spinner
     */
    public abstract void selectCategorie(List<Category> categories, Spinner s);
}
