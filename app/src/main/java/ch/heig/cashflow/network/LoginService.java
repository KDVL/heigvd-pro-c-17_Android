package ch.heig.cashflow.network;

import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginService implements  DownloadCallback<APIManager.Result> {

    Callback callback;

    public LoginService(Callback call, String login, String password){
        callback = call;

        APIManager manager = new APIManager(this,
                                        false,
                                        APIManager.METHOD.POST);

        HashMap<String, String> params = new HashMap<>();
        params.put("usernameOrEmail", login);
        params.put("password", password);

        manager.setPostParams(params);
        manager.execute(Config.LOGIN);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        callback.loginFinished(result.responseCode == 200);

    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        return callback.getActiveNetworkInfo();
    }

    public interface Callback extends BaseCallback{
        void loginFinished(boolean isLogged);
    }
}
