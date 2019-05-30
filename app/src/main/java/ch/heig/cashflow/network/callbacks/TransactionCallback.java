/**
 * A transaction callback used to perform unit tests
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see ch.heig.cashflow.network.services.TransactionService
 */

package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.services.TransactionService;

public class TransactionCallback implements TransactionService.Callback {

    private Context context;
    private ServicesFragment fragment;
    private TransactionService ts;

    /**
     * The TransactionCallback constructor
     *
     * @param context  The application context
     * @param fragment The linked fragment
     */
    public TransactionCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.ts = new TransactionService(this);
    }

    /**
     * Get one transaction related to a registered {@code User}
     *
     * @param id The transaction id
     */
    public void get(long id) {
        ts.get(id);
    }

    /**
     * Add a new transaction related to a registered {@code User}
     *
     * @param transaction The {@code Transaction}
     */
    public void add(Transaction transaction) {
        ts.add(transaction);
    }

    /**
     * Update a transaction related to a registered {@code User}
     *
     * @param transaction The {@code Transaction}
     */
    public void update(Transaction transaction) {
        ts.update(transaction);
    }

    /**
     * Disable a transaction related to a registered {@code User}
     *
     * @param transaction The {@code Transaction}
     */
    public void delete(Transaction transaction) {
        ts.delete(transaction);
    }

    /**
     * Display an error when the API connection failed
     *
     * @param error
     */
    @Override
    public void connectionFailed(String error) {
        System.err.println("DOWN  - TransactionService " + error);
    }

    /**
     * Display a success message on operation finished
     *
     * @param isFinished The operation status
     */
    @Override
    public void operationFinished(boolean isFinished) {
        System.out.println("OK    TransactionService found");
    }

    /**
     * Display a message about the transaction status
     *
     * @param transaction The categories list
     */
    @Override
    public void getFinished(Transaction transaction) {
        if (transaction == null)
            System.err.println("EMPTY - TransactionService");
        else
            System.out.println("OK    TransactionService found " + transaction);
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
