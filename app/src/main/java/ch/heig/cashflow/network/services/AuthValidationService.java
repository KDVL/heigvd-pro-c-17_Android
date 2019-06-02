/**
 * Auth Validation
 *
 * @author Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.network.services;

import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;

public class AuthValidationService extends APIService implements DownloadCallback<APIManager.Result> {

    private Callback callback;

    /**
     * The AuthValidationService constructor
     *
     * @param callback The callback class
     */
    public AuthValidationService(Callback callback) {
        super(callback);
        this.callback = callback;
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.AUTH_VERIF);
    }

    /**
     * Call callback
     *
     * @param result The request result from APIManager
     */
    @Override
    public void updateFromDownload(APIManager.Result result) {
        if (callback != null)
            callback.authVerification(result.responseCode == 200);
        else if (result == null)
            callback.authVerification(false);
    }

    /**
     * The callback interface used by client
     */
    public interface Callback extends APICallback {
        void authVerification(boolean isLogged);
    }
}
