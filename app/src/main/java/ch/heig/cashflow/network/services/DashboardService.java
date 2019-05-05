package ch.heig.cashflow.network.services;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Dashboard;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;

public class DashboardService implements DownloadCallback<APIManager.Result> {

    private Callback callback;
    private Gson gson = new Gson();

    public DashboardService(Callback call) {
        callback = call;
    }

    // GetAll : GET /api/dashboard
    public void getAll() {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.DASHBOARD);
    }

    // PerType : GET /api/dashboard/date/YYYY/MM
    public void getAll(String year, String month) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.DASHBOARD_DATE + year + "/" + month);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        Gson gson = new Gson();
        Dashboard[] dashboards;

        if (result.responseCode != 200) {
            String exception = result.exception == null ? "" : result.exception.toString();
            callback.connectionFailed(exception);
            return;
        }

        dashboards = gson.fromJson(result.resultString, Dashboard[].class);

        if (result.tag.contains(Config.DASHBOARD))
            callback.getFinished(Arrays.asList(dashboards));
    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback {
        void connectionFailed(String error);

        void getFinished(List<Dashboard> dashboards);
    }
}
