package ch.heig.cashflow.network;

import android.content.Context;

import java.util.HashMap;



// GetOne : GET /api/transactions/{id}
// Update : PUT /api/transactions/{id}
// Delete : DELETE /api/transactions/{id}

public class TransactionService implements DownloadCallback<APIManager.Result> {

    Callback callback;

    public TransactionService(Callback call, long id) {
        callback = call;

        APIManager manager = new APIManager(this,
                false,
                APIManager.METHOD.GET);

        manager.execute(Config.TRANSACTIONS + "/" + id);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {
        callback.transactionReceived(result.responseCode == 200);
    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback {
        void transactionReceived(boolean isReceived);
        //void get(Transaction transaction);
    }
}
