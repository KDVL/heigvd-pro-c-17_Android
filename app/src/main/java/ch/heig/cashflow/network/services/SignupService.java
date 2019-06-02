/**
 * Signup service used by SignupActivity
 *
 * @author Kevin DO VALE
 * @version 1.0
 * @see ch.heig.cashflow.activites.SignupActivity
 */

package ch.heig.cashflow.network.services;

import com.google.gson.Gson;

import ch.heig.cashflow.models.User;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;

public class SignupService extends APIService implements DownloadCallback<APIManager.Result> {

    private LoginService.Callback callback;
    private User user;

    /**
     * The SignupService constructor
     *
     * @param callback The callback class
     * @param user     The user
     */
    public SignupService(LoginService.Callback callback, User user) {
        super(callback);
        this.callback = callback;
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
     * Call LoginService if user is correctly signed up
     *
     * @param result The request result from APIManager
     */
    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (callback == null) return;
        if (result != null && result.responseCode == 201) {
            new LoginService(callback, user.getEmail(), user.getPassword());
        } else {
            callback.loginFinished(false);
        }
    }
}
