package ch.heig.cashflow.network;

import android.content.Context;
import android.net.NetworkInfo;

public class SigninService  implements  DownloadCallback<APIManager.Result> {

    Callback callback;

    SigninService(Callback call, String login, String password, String name){
        callback = call;

        APIManager manager = new APIManager(this,
                false,
                APIManager.METHOD.POST);
        manager.execute(Config.LOGIN);
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