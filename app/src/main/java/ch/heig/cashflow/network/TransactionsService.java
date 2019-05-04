package ch.heig.cashflow.network;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;

// GetAll : GET /api/transactions
// Add : POST /api/transactions

public class TransactionsService implements  DownloadCallback<APIManager.Result> {

    Callback callback;

    public TransactionsService(Callback call){
        callback = call;

        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.TRANSACTIONS);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {
        callback.transactionsReceived(result.responseCode == 200);
        //callback.getAll(result.);
    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback {
        void transactionsReceived(boolean isReceived);
        void getAll(ArrayList transactions);
    }
}
