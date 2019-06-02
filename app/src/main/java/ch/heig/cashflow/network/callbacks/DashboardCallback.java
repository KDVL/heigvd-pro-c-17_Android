/**
 * A dashboard callback used to perform unit tests
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see ch.heig.cashflow.network.services.DashboardService
 */

package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.network.services.DashboardService;

public class DashboardCallback implements DashboardService.Callback {

    private Context context;
    private ServicesFragment fragment;
    private DashboardService ds;

    /**
     * The DashboardCallback constructor
     *
     * @param context  The application context
     * @param fragment The linked fragment
     */
    public DashboardCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.ds = new DashboardService(this);
    }

    /**
     * Get the current month dashboard related to a registered {@code User}
     */
    public void getAll() {
        ds.getAll();
    }

    /**
     * Get a specific month dashboard related to a registered {@code User}
     */
    public void getAllByMonth() {
        ds.getAllByMonth();
    }

    /**
     * Display an error when the API connection failed
     *
     * @param error
     */
    @Override
    public void connectionFailed(String error) {
        System.err.println("DOWN  - DashboardService " + error);
    }

    @Override
    public void getFinished(Budget dashboard) {
        System.out.println("OK    DashboardService found " + dashboard);
    }

    /**
     * Get the application context
     *
     * @return Context The application context
     */
    @Override
    public Context getContext() {
        return context;
    }
}
