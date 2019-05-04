package ch.heig.cashflow.network.services;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Income;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;

public class TransactionsService implements DownloadCallback<APIManager.Result> {

    private Callback callback;
    private Gson gson = new Gson();

    public TransactionsService(Callback call) {
        callback = call;
    }

    // GetAll : GET /api/transactions
    public void getAll() {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS);
    }

    // ByMonth : GET /api/transactions/YYYY/MM
    public void getAll(String year, String month) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS + "/" + year + "/" + month);
    }

    // PerType : GET /api/transactions/type/{type}
    public void getType(Type type) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_TYPE + type);
    }

    // PerTypeByMonth : GET /api/transactions/type/{type}/YYYY/MM
    public void getType(Type type, String year, String month) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_TYPE + type + "/" + year + "/" + month);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        Transaction[] transactions = new Transaction[0];

        if (result.responseCode != 200) {
            String exception = result.exception == null ? "" : result.exception.toString();
            callback.connectionFailed(exception);
            return;
        }

        switch (result.tag) {

            case Config.TRANSACTIONS: // GetAll : GET /api/transactions
                transactions = gson.fromJson(result.resultString, Transaction[].class);
                callback.getAllFinished(Arrays.asList(transactions));
                break;

            case Config.TRANSACTIONS_TYPE: // PerType : GET /api/transactions/type/{type}
                switch (gson.fromJson(result.resultString, JsonObject.class).get("type").toString()) {
                    case "EXPENSE":
                        transactions = gson.fromJson(result.resultString, Expense[].class);
                        break;
                    case "INCOME":
                        transactions = gson.fromJson(result.resultString, Income[].class);
                        break;
                }
                callback.getTypeFinished(Arrays.asList(transactions));
                break;
        }

    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback {
        void connectionFailed(String error);

        void getAllFinished(List<Transaction> transactions);

        void getTypeFinished(List<Transaction> transactions);
    }
}
