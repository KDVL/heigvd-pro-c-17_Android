package ch.heig.cashflow.network.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Income;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;


public class TransactionService implements DownloadCallback<APIManager.Result> {

    private Callback callback;
    private Gson gson = new Gson();

    public TransactionService(Callback call, long id) {
        callback = call;
    }

    // GetOne : GET /api/transactions/{id}
    public void get(long id) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTION + id);
    }

    // Add : POST /api/transactions
    public void add(Transaction transaction) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.POST);
        manager.setPostParams(getTransactionParams(transaction));
        manager.execute(Config.TRANSACTIONS);
    }

    // Update : PUT /api/transactions/{id}
    public void update(Transaction transaction) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.PUT);
        manager.setPostParams(getTransactionParams(transaction));
        manager.execute(Config.TRANSACTION + transaction.getID());
    }

    // Delete : DELETE /api/transactions/{id}
    public void delete(Transaction transaction) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.DELETE);
        manager.execute(Config.TRANSACTION + transaction.getID());
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (result.responseCode != 200) {
            callback.connectionFailed(result.exception.toString());
            return;
        }

        switch (result.method) {
            case GET: // GetOne : GET /api/transactions/{id}
                Gson gson = new Gson();
                switch (gson.fromJson(result.resultString, JsonObject.class).get("type").toString()) {
                    case "EXPENSE":
                        callback.getFinished(gson.fromJson(result.resultString, Expense.class));
                        break;
                    case "INCOME":
                        callback.getFinished(gson.fromJson(result.resultString, Income.class));
                        break;
                }
                break;

            case POST:  // Add : POST /api/transactions
                callback.addFinished(true);
                break;

            case PUT: // Update : PUT /api/transactions/{id}
                callback.updateFinished(true);
                break;

            case DELETE: // Delete : DELETE /api/transactions/{id}
                callback.deleteFinished(true);
                break;
        }
    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    private String getTransactionParams(Transaction transaction) {
        switch (transaction.getType()) {
            case EXPENSE:
                return gson.toJson(transaction, Expense.class);
            case INCOME:
                return gson.toJson(transaction, Income.class);
        }
        return null;
    }

    public interface Callback extends BaseCallback {
        void connectionFailed(String error);

        void getFinished(Transaction transaction);

        void addFinished(boolean isAdded);

        void updateFinished(boolean isUpdated);

        void deleteFinished(boolean isDeleted);
    }
}
