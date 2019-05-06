package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.callbacks.CategoriesCallback;
import ch.heig.cashflow.network.callbacks.CategoryCallback;
import ch.heig.cashflow.network.callbacks.TransactionCallback;
import ch.heig.cashflow.network.callbacks.TransactionsCallback;

public class ServicesFragment extends Fragment {

    public boolean callDone = false;

    private TransactionsCallback tc;
    private boolean tsState;

    public ServicesFragment() {
    }

    public static ServicesFragment newInstance() {
        return new ServicesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testCategoriesService();
        testTransactionsService();

        testCategoryService();
        testTransactionService();
    }

    public void testCategoriesService(){
        new CategoriesCallback(getContext(), this).getAll();
        new CategoriesCallback(getContext(), this).getType(Type.EXPENSE);
    }

    public void testTransactionsService(){
        new TransactionsCallback(getContext(), this).getAll();
        new TransactionsCallback(getContext(), this).getAll(2019,5);
        new TransactionsCallback(getContext(), this).getType(Type.EXPENSE);
        new TransactionsCallback(getContext(), this).getType(Type.INCOME,2019,5);
    }

    public void testCategoryService(){
        Category cat = new Category(1,"New","icon",Type.EXPENSE,123,true);

        new CategoryCallback(getContext(), this).get(5);
        new CategoryCallback(getContext(), this).add(cat);
        new CategoryCallback(getContext(), this).update(cat);
        //new CategoryCallback(getContext(), this).delete(cat);
    }

    public void testTransactionService(){
        Category cat = new Category(1,"New","icon",Type.EXPENSE,123,true);
        Expense expense = new Expense(5,"2019-05-01",cat,123,"desc");

        new TransactionCallback(getContext(), this).get(5);
        new TransactionCallback(getContext(), this).add(expense);
        new TransactionCallback(getContext(), this).update(expense);
        //new TransactionCallback(getContext(), this).delete(expense);
    }

    public boolean isTransactionsServiceUp() {
        return tsState;
    }

    public void setTransactionsServiceState(boolean transactionsServiceUp) {
        this.tsState = transactionsServiceUp;
    }
}
