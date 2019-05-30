/**
 * Service used to retrieve a dashboard of transactions/categories through the API
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/Dashboard
 */

package ch.heig.cashflow.network.services;

import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;
import ch.heig.cashflow.utils.SelectedDate;

public class DashboardService extends APIService {

    private Callback callback;

    /**
     * The DashboardService constructor
     *
     * @param callback The callback class
     */
    public DashboardService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    /**
     * Get the current month dashboard related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/dashboard
     */
    public void getAll() {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.DASHBOARD);
    }

    /**
     * Get a specific month dashboard related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/dashboard/date/YYYY/MM
     */
    public void getAllByMonth() {
        SelectedDate date = SelectedDate.getInstance();
        int year = date.getYear();
        int month = date.getMonth() + 1;
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.DASHBOARD_DATE + year + "/" + month);
    }

    /**
     * @param result the result
     */
    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (!checkResponse(result))
            return;

        Budget dashboard = gson.fromJson(result.resultString, Budget.class);

        if (result.tag.contains(Config.DASHBOARD))
            callback.getFinished(dashboard);
    }

    /**
     * The callback interface used by client
     */
    public interface Callback extends APICallback {
        void getFinished(Budget budget);
    }
}
