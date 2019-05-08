package ch.heig.cashflow.network.services;

import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Dashboard;
import ch.heig.cashflow.models.SelectedDate;
import ch.heig.cashflow.network.APIManager;
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

        Dashboard dashboard = gson.fromJson("{\"name\":\"Budget Global - Mai 2019\",\"expense\":503475,\"income\":199305,\"budget\":-304167,\"categories\":[{\"name\":\"Salaire\",\"expense\":0,\"income\":199305,\"budget\":199305},{\"name\":\"Shopping\",\"expense\":124374,\"income\":0,\"budget\":125868},{\"name\":\"Voyage\",\"expense\":124374,\"income\":0,\"budget\":125868},{\"name\":\"Assurance\",\"expense\":13315,\"income\":0,\"budget\":125868},{\"name\":\"Divers\",\"expense\":13315,\"income\":0,\"budget\":125868}]}", Dashboard.class);

        if (result.tag.contains(Config.DASHBOARD))
            callback.getFinished(dashboard);
    }

    public interface Callback extends APICallback {
        void getFinished(Dashboard dashboard);
    }
}
