package ch.heig.cashflow.network.services;

import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;
import ch.heig.cashflow.utils.SelectedDate;

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
    public void getAllByMonth() {
        SelectedDate date = SelectedDate.getInstance();
        int year = date.getYear();
        int month = date.getMonth() + 1;
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.DASHBOARD_DATE + year + "/" + month);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (!checkResponse(result))
            return;

        Budget dashboard = gson.fromJson(result.resultString, Budget.class);

        if (result.tag.contains(Config.DASHBOARD))
            callback.getFinished(dashboard);
    }

    public interface Callback extends APICallback {
        void getFinished(Budget budget);
    }
}
