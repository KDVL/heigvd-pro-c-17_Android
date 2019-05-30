/**
 * Service used to manage a specific transaction through the API
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/GetOneTransaction
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/AddTransaction
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/UpdateTransaction
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/DeleteTransaction
 */

package ch.heig.cashflow.network.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.models.Income;
import ch.heig.cashflow.models.PostTransaction;
import ch.heig.cashflow.models.Transaction;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;

public class TransactionService extends APIService {

    private Callback callback;

    /**
     * The TransactionService constructor
     *
     * @param callback The callback class
     */
    public TransactionService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    /**
     * Get one transaction related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/transactions/{id}
     *
     * @param id The transaction id
     */
    public void get(long id) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS + id);
    }

    /**
     * Add a new transaction related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: POST
     * REST API URI: /api/transactions
     *
     * @param transaction The {@code Transaction}
     */
    public void add(Transaction transaction) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.POST);
        manager.setPostParams(getTransactionParams(transaction));
        manager.execute(Config.TRANSACTIONS);
    }

    /**
     * Update a transaction related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: PUT
     * REST API URI: /api/transactions/{id}
     *
     * @param transaction The {@code Transaction}
     */
    public void update(Transaction transaction) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.PUT);
        manager.setPostParams(getTransactionParams(transaction));
        manager.execute(Config.TRANSACTIONS + transaction.getID());
    }

    /**
     * Disable a transaction related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: DELETE
     * REST API URI: /api/transactions/{id}
     *
     * @param transaction The {@code Transaction}
     */
    public void delete(Transaction transaction) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.DELETE);
        manager.execute(Config.TRANSACTIONS + transaction.getID());
    }

    /**
     * Set the operation status received from the API response
     *
     * @param result The request result from APIManager
     */
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

    /**
     * Get all the transaction params as JSON
     *
     * @param transaction The {@code Transaction}
     * @return String The params as JSON
     */
    private String getTransactionParams(Transaction transaction) {
        return gson.toJson(new PostTransaction(transaction));
    }

    /**
     * The callback interface used by client
     */
    public interface Callback extends APICallback {
        void getFinished(Transaction transaction);

        void operationFinished(boolean isFinished);
    }
}
