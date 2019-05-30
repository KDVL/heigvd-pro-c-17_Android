/**
 * A transactions callback used to perform unit tests
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import java.util.List;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionsService;
import ch.heig.cashflow.utils.Type;

public class TransactionsCallback implements TransactionsService.Callback {

    private Context context;
    private ServicesFragment fragment;
    private TransactionsService ts;

    /**
     * The TransactionsCallback constructor
     *
     * @param context  The application context
     * @param fragment The linked fragment
     */
    public TransactionsCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.ts = new TransactionsService(this);
    }

    public void getAll() {
        ts.getAll();
    }

    public void getAll(int year, int month) {
        ts.getAll(year, month);
    }

    public void getType(Type type) {
        ts.getType(type);
    }

    public void getTypeByMonth(Type type) {
        ts.getTypeByMonth(type);
    }

    /**
     * Display an error when the API connection failed
     *
     * @param error
     */
    @Override
    public void connectionFailed(String error) {
        System.err.println("DOWN  - TransactionsService " + error);
    }

    /**
     * Display a message about the transactions list status
     *
     * @param transactions The categories list
     */
    @Override
    public void getFinished(List<Transaction> transactions) {
        if (transactions.size() < 1)
            System.err.println("EMPTY LIST - TransactionsService");
        else
            System.out.println("OK    TransactionsService found " + transactions);
    }

    /**
     * Get the application context
     *
     * @return Context The application context
     */
    @Override
    public Context getContext() {
        return context;
    }
}
