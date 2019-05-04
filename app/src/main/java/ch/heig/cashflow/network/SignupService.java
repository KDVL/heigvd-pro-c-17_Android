package ch.heig.cashflow.network;

import android.content.Context;
import android.net.NetworkInfo;

public class SignupService implements  DownloadCallback<APIManager.Result> {

    Callback callback;

    SignupService(Callback call, String login, String password, String name){
        callback = call;

        APIManager manager = new APIManager(this,
                false,
                APIManager.METHOD.POST);
        manager.execute(Config.AUTH_SIGNUP);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {
        callback.loginFinished(true);
    }


    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback{
        void loginFinished(boolean isLogged);
    }
}