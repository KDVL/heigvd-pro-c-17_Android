package ch.heig.cashflow.network;

import android.content.Context;

import com.google.gson.Gson;
import ch.heig.cashflow.models.User;

public class SignupService implements  DownloadCallback<APIManager.Result> {

    private LoginService.Callback callback;
    private User user;

    public SignupService(LoginService.Callback call, User user){
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

    @Override
    public void updateFromDownload(APIManager.Result result) {
        if( result.responseCode == 201){
            new LoginService(callback, user.getEmail(), user.getPassword());
        }else{
            callback.loginFinished(false);
        }
    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }
}