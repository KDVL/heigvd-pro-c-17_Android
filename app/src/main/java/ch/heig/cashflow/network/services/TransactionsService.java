package ch.heig.cashflow.network.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Income;
import ch.heig.cashflow.models.SelectedDate;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.utils.Type;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;

public class TransactionsService extends APIService {

    Callback callback;

    public TransactionsService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    // GetAll : GET /api/transactions
    public void getAll() {
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.TRANSACTIONS);
    }

    // ByMonth : GET /api/transactions/date/YYYY/MM
    public void getAll(int year, int month) {
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.TRANSACTIONS_DATE + year + "/" + month);
    }

    // PerType : GET /api/transactions/type/{type}
    public void getType(Type type) {
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.TRANSACTIONS_TYPE + type);
    }

    // PerTypeByMonth : GET /api/transactions/type/{type}/date/YYYY/MM
    public void getTypeByMonth(Type type) {
        SelectedDate date = SelectedDate.getInstance();
        int year = date.getYear();
        int month = date.getMonth() + 1;
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.TRANSACTIONS_TYPE + type + "/date/" + year + "/" + month);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (!checkResponse(result))
            return;

        if (result.tag.contains(Config.TRANSACTIONS_TYPE)) {

            if (result.tag.contains("EXPENSE")) { // PerType : GET /api/transactions/type/EXPENSE
                Transaction[] expenses = gson.fromJson(result.resultString, Expense[].class);
                callback.getFinished(Arrays.asList(expenses));
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

    public interface Callback extends APICallback {
        void getFinished(List<Transaction> transactions);
    }
}
