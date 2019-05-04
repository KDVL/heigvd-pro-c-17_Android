package ch.heig.cashflow.network.services;

import android.content.Context;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
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
    public void getAll(int year, int month) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS + "/" + year + "/" + month);
    }

    // PerType : GET /api/transactions/type/{type}
    public void getType(Type type) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_TYPE + type);
    }

    // PerTypeByMonth : GET /api/transactions/type/{type}/YYYY/MM
    public void getType(Type type, int year, int month) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_TYPE + type + "/" + year + "/" + month);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (result.responseCode != 200) {
            String exception = result.exception == null ? "" : result.exception.toString();
            callback.connectionFailed(exception);
            return;
        }

        switch (result.tag) {

            case Config.TRANSACTIONS: // GetAll : GET /api/transactions
                List<Transaction> transactions = new ArrayList<>();
                JsonArray jsonArray = gson.fromJson(result.resultString, JsonArray.class);
                for (JsonElement obj : jsonArray) {
                    JsonElement element = ((JsonObject) obj).get("type");
                    switch (gson.fromJson(element, String.class)) {
                        case "EXPENSE":
                            transactions.add(gson.fromJson(obj.toString(), Expense.class));
                            break;
                        case "INCOME":
                            transactions.add(gson.fromJson(obj.toString(), Income.class));
                            break;
                    }
                }

                callback.getAllFinished(transactions);
                break;

            case Config.TRANSACTIONS_TYPE_EXPENSE: // PerType : GET /api/transactions/type/EXPENSE
                Transaction[] expenses = gson.fromJson(result.resultString, Expense[].class);
                callback.getTypeFinished(Arrays.asList(expenses));
                break;

            case Config.TRANSACTIONS_TYPE_INCOME: // PerType : GET /api/transactions/type/INCOME
                Transaction[] incomes = gson.fromJson(result.resultString, Income[].class);
                callback.getTypeFinished(Arrays.asList(incomes));
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
