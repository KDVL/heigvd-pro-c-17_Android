package ch.heig.cashflow.network.services;

import android.content.Context;

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

    // ByMonth : GET /api/transactions/date/YYYY/MM
    public void getAll(int year, int month) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_DATE + year + "/" + month);
    }

    // PerType : GET /api/transactions/type/{type}
    public void getType(Type type) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_TYPE + type);
    }

    // PerTypeByMonth : GET /api/transactions/type/{type}/date/YYYY/MM
    public void getType(Type type, int year, int month) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_TYPE + type + "/date/" + year + "/" + month);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (result.responseCode != 200 || result.resultString.equals("null")) {
            String exception = result.exception == null ? "" : result.exception.toString();
            callback.connectionFailed(exception);
            return;
        }

        if (result.tag.contains(Config.TRANSACTIONS_TYPE)) {

            if (result.tag.contains("EXPENSE")) { // PerType : GET /api/transactions/type/EXPENSE
                Transaction[] expenses = gson.fromJson(result.resultString, Expense[].class);
                /** MODIF ALEKS POUT TABLEAU NULL SI NOUVEAU UTILISATEUR */
                if(expenses != null){
                    callback.getFinished(Arrays.asList(expenses));
                }else{
                    callback.getFinished(new ArrayList<Transaction>());
                }

            } else if (result.tag.contains("INCOME")) { // PerType : GET /api/transactions/type/INCOME
                Transaction[] incomes = gson.fromJson(result.resultString, Income[].class);
                callback.getFinished(Arrays.asList(incomes));
            }

        } else if (result.tag.contains(Config.TRANSACTIONS)) { // GetAll : GET /api/transactions
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
            callback.getFinished(transactions);
        }

    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback {
        void connectionFailed(String error);

        void getFinished(List<Transaction> transactions);
    }
}
