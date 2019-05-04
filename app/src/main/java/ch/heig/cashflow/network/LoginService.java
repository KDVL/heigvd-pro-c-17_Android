package ch.heig.cashflow.network;

import android.content.Context;
import android.net.NetworkInfo;

import com.google.gson.Gson;

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
        manager.execute(Config.AUTH_SIGNIN);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        Gson gson = new Gson();

        HashMap<String,String> res = new HashMap<>();
        res = gson.fromJson(result.resultString, res.getClass());

        String accessToken = res.get("accessToken");

        if(accessToken != "" && accessToken != null){
            TokenHolder.saveToken(getContext(), accessToken);
            callback.loginFinished(result.responseCode == 200);
        }else{
            callback.loginFinished(false);
        }
    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback {
        void loginFinished(boolean isLogged);
    }
}
