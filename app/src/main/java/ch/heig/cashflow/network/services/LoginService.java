/**
 * Login service used by LoginActivity
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.activites.LoginActivity
 */

package ch.heig.cashflow.network.services;

import com.google.gson.Gson;

import java.util.HashMap;

import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;
import ch.heig.cashflow.network.utils.TokenHolder;

public class LoginService extends APIService implements DownloadCallback<APIManager.Result> {

    private Callback callback;

    /**
     * The LoginService constructor
     *
     * @param callback The callback class
     * @param email    The user email
     * @param password The user password
     */
    public LoginService(Callback callback, String email, String password) {
        super(callback);
        this.callback = callback;

        APIManager manager = new APIManager(this,
                false,
                APIManager.METHOD.POST);

        HashMap<String, String> params = new HashMap<>();
        params.put("usernameOrEmail", email);
        params.put("password", password);

        Gson gson = new Gson();

        manager.setPostParams(gson.toJson(params));
        manager.execute(Config.AUTH_SIGNIN);
    }

    /**
     * Save token result if user is logged successfully
     *
     * @param result The request result from APIManager
     */
    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (callback == null) return;

        Gson gson = new Gson();

        HashMap<String, String> res = new HashMap<>();
        res = gson.fromJson(result.resultString, res.getClass());

        if (res == null) {
            callback.loginFinished(false);
            return;
        }

        String accessToken = res.get("accessToken");

        if (accessToken != "" && accessToken != null) {
            TokenHolder.saveToken(getContext(), accessToken);
            callback.loginFinished(result.responseCode == 200);
        } else {
            callback.loginFinished(false);
        }
    }

    /**
     * The callback interface used by client
     */
    public interface Callback extends APICallback {
        void loginFinished(boolean isLogged);
    }
}
