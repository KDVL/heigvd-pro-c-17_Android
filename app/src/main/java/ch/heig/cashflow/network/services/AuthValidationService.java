/**
 * Auth Validation
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */
package ch.heig.cashflow.network.services;

import android.content.Context;

import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;

public class AuthValidationService implements DownloadCallback<APIManager.Result> {

    Callback callback;

    /**
     * Constructor
     * @param call the callback
     *
     */
    public AuthValidationService(Callback call){
        callback = call;

        APIManager manager = new APIManager(this,
                true,
                APIManager.METHOD.GET);
        manager.execute(Config.AUTH_VERIF);
    }

    /**
     * call callback
     * @param result the request result from APIManager
     */
    @Override
    public void updateFromDownload(APIManager.Result result) {
        callback.authVerification(result.responseCode == 200);
    }

    /**
     * get context application
     */
    @Override
    public Context getContext() {
        return callback.getContext();
    }

    /**
     * Callback interface used by client
     *
     */
    public interface Callback extends BaseCallback {
        void authVerification(boolean isLogged);
    }
}
