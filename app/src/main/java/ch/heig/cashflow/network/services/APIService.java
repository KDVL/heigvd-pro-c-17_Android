package ch.heig.cashflow.network.services;

import android.content.Context;

import com.google.gson.Gson;

import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;

public abstract class APIService implements DownloadCallback<APIManager.Result> {

    protected APICallback callback;
    protected Gson gson = new Gson();

    public APIService(APICallback call) {
        callback = call;
    }

    public boolean checkResponse(APIManager.Result result) {
        if(result == null || callback == null) return false;

        if (result.responseCode != 200 || result.resultString.equals("null")) {
            String exception = result.exception == null ? "" : result.exception.toString();
            callback.connectionFailed(exception);
            return false;
        }
        return true;
    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    @Override
    public abstract void updateFromDownload(APIManager.Result result);

    public interface APICallback extends BaseCallback {
        void connectionFailed(String error);
    }
}
