/**
 * Service used to retrieve all the transactions through the API
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/GetAllTransactions
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/GetAllByMonth
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/TransactionPerType
 */

package ch.heig.cashflow.network.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Income;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;
import ch.heig.cashflow.utils.SelectedDate;
import ch.heig.cashflow.utils.Type;

public class TransactionsService extends APIService {

    private Callback callback;

    /**
     * The TransactionsService constructor
     *
     * @param callback The callback class
     */
    public TransactionsService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    /**
     * Get all transactions related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/transactions
     */
    public void getAll() {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS);
    }

    /**
     * Get all transactions by month related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/transactions/date/YYYY/MM
     *
     * @param year  The desired year
     * @param month The desired month
     */
    public void getAll(int year, int month) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_DATE + year + "/" + month);
    }

    /**
     * Get all the transactions related to a registered {@code User} in a specific {@code Type}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/transactions/type/{type}
     *
     * @param type The {@code Type}
     */
    public void getType(Type type) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_TYPE + type);
    }

    /**
     * Get all the transactions for a month related to a registered {@code User} in a specific {@code Type}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/transactions/type/{type}/date/YYYY/MM
     *
     * @param type The {@code Type}
     */
    public void getTypeByMonth(Type type) {
        SelectedDate date = SelectedDate.getInstance();
        int year = date.getYear();
        int month = date.getMonth() + 1;
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS_TYPE + type + "/date/" + year + "/" + month);
    }

    /**
     * Retrieve the transactions list and push it
     *
     * @param result The request result from APIManager
     */
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

    /**
     * The callback interface used by client
     */
    public interface Callback extends APICallback {
        void getFinished(List<Transaction> transactions);
    }
}
