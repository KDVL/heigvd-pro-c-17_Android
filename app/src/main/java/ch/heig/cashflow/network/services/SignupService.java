/**
 * Signup service used by SignupActivity
 *
 * @authors Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.activites.SignupActivity
 */
package ch.heig.cashflow.network.services;

import android.content.Context;

import com.google.gson.Gson;

import ch.heig.cashflow.models.User;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;

public class SignupService implements DownloadCallback<APIManager.Result> {

    private LoginService.Callback callback;
    private User user;

    /**
     * Constructor
     * @param call the callback
     * @param user user informations
     */
    public SignupService(LoginService.Callback call, User user) {
        callback = call;
        this.user = user;

        APIManager manager = new APIManager(this,
                false,
                APIManager.METHOD.POST);

        Gson gson = new Gson();
        String json = gson.toJson(user);
        manager.setPostParams(json);
        manager.execute(Config.AUTH_SIGNUP);
    }

    /**
     * call LoginService if user is correctly Signup
     * @param result the request result from APIManager
     */
    @Override
    public void updateFromDownload(APIManager.Result result) {
        if (result.responseCode == 201) {
            new LoginService(callback, user.getEmail(), user.getPassword());
        } else {
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
}