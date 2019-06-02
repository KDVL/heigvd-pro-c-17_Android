/**
 * Abstract class used by all services
 *
 * <p>
 * Used primarily to avoid code redundancy
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.network.services;

import android.content.Context;

import com.google.gson.Gson;

import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;

public abstract class APIService implements DownloadCallback<APIManager.Result> {

    protected Gson gson;
    protected APICallback callback;

    /**
     * The APIService constructor
     *
     * @param callback The callback
     */
    protected APIService(APICallback callback) {
        this.gson = new Gson();
        this.callback = callback;
    }

    /**
     * Check the API response
     *
     * @param response The API response
     * @return boolean True if the API response is correct and usable, false otherwise
     */
    protected boolean checkResponse(APIManager.Result response) {

        if (response == null || callback == null)
            return false;

        if (response.responseCode != 200 || response.resultString.equals("null")) {
            String exception = response.exception == null ? "" : response.exception.toString();
            callback.connectionFailed(exception);
            return false;
        }

        return true;
    }

    /**
     * Get the application context
     *
     * @return Context The application context
     */
    @Override
    public Context getContext() {
        return callback.getContext();
    }

    /**
     * The action to perform on update
     *
     * <p>
     * Indicates that the callback handler needs to update its appearance or information based on
     * the result of the task. Expected to be called from the main thread.
     *
     * @param result the result
     */
    @Override
    public abstract void updateFromDownload(APIManager.Result result);

    /**
     * The callback interface used by client
     */
    protected interface APICallback extends BaseCallback {
        void connectionFailed(String error);
    }
}
