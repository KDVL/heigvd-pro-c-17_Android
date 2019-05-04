/**
 * Login service used by LoginActivity
 * @see ch.heig.cashflow.activites.LoginActivity
 *
 *
 * @authors Kevin DO VALE
 * @version 1.0
 */

package ch.heig.cashflow.network.services;

import android.content.Context;
import com.google.gson.Gson;

import java.util.HashMap;

import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;
import ch.heig.cashflow.network.utils.TokenHolder;

public class LoginService implements DownloadCallback<APIManager.Result> {

    Callback callback;

    /**
     * Constructor
     * @param call the callback
     * @param email email user
     * @param password password user
     *
     */
    public LoginService(Callback call, String email, String password){
        callback = call;

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
     * @param result the request result from APIManager
     */
    @Override
    public void updateFromDownload(APIManager.Result result) {

        Gson gson = new Gson();

        HashMap<String,String> res = new HashMap<>();
        res = gson.fromJson(result.resultString, res.getClass());

        if(res == null){
            callback.loginFinished(false);
            return;
        }

        String accessToken = res.get("accessToken");

        if(accessToken != "" && accessToken != null){
            TokenHolder.saveToken(getContext(), accessToken);
            callback.loginFinished(result.responseCode == 200);
        }else{
            callback.loginFinished(false);
        }
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
        void loginFinished(boolean isLogged);
    }
}
