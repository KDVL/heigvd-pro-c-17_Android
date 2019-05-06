package ch.heig.cashflow.network.services;

import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Dashboard;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.APIService;
import ch.heig.cashflow.network.utils.Config;

public class DashboardService extends APIService {

    Callback callback;

    public DashboardService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    // GetAll : GET /api/dashboard
    public void getAll() {
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.DASHBOARD);
    }

    // PerType : GET /api/dashboard/date/YYYY/MM
    public void getAll(String year, String month) {
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.DASHBOARD_DATE + year + "/" + month);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (!checkResponse(result))
            return;

        Dashboard[] dashboards;
        dashboards = gson.fromJson(result.resultString, Dashboard[].class);

        if (result.tag.contains(Config.DASHBOARD))
            callback.getFinished(Arrays.asList(dashboards));
    }

    public interface Callback extends APICallback {
        void getFinished(List<Dashboard> dashboards);
    }
}
