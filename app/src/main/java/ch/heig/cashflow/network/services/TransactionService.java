package ch.heig.cashflow.network.services;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Income;
import ch.heig.cashflow.models.PostTransaction;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;

public class TransactionService extends APIService {

    Callback callback;

    public TransactionService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    // GetOne : GET /api/transactions/{id}
    public void get(long id) {
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.TRANSACTIONS + id);
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
        manager.execute(Config.TRANSACTIONS + transaction.getID());
    }

    // Delete : DELETE /api/transactions/{id}
    public void delete(Transaction transaction) {
        new APIManager(this, true, APIManager.METHOD.DELETE).execute(Config.TRANSACTIONS + transaction.getID());
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (!checkResponse(result))
            return;

        switch (result.method) {
            case GET: // GetOne : GET /api/transactions/{id}
                JsonElement obj = gson.fromJson(result.resultString, JsonElement.class);
                JsonElement element = ((JsonObject) obj).get("type");
                switch (gson.fromJson(element, String.class)) {
                    case "EXPENSE":
                        callback.getFinished(gson.fromJson(result.resultString, Expense.class));
                        break;
                    case "INCOME":
                        callback.getFinished(gson.fromJson(result.resultString, Income.class));
                        break;
                }
                break;

            case POST:  // Add : POST /api/transactions
            case PUT: // Update : PUT /api/transactions/{id}
            case DELETE: // Delete : DELETE /api/transactions/{id}
                callback.operationFinished(true);
                break;
        }
    }

    private String getTransactionParams(Transaction transaction) {
        return gson.toJson(new PostTransaction(transaction));
    }

    public interface Callback extends APICallback {
        void getFinished(Transaction transaction);

        void operationFinished(boolean isFinished);
    }
}
