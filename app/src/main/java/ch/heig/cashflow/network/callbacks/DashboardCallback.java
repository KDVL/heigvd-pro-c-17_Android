package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import java.util.List;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Dashboard;
import ch.heig.cashflow.network.services.DashboardService;

public class DashboardCallback implements DashboardService.Callback {

    Context context;
    ServicesFragment fragment;
    DashboardService ds;

    public DashboardCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.ds = new DashboardService(this);
    }

    public void getAll() {
        ds.getAll();
    }

    public void getAll(int year, int month) {
        ds.getAll(year, month);
    }

    @Override
    public void connectionFailed(String error) {
        System.err.println("DOWN  - DashboardService " + error);
    }

    @Override
    public void getFinished(Dashboard dashboard) {
            System.out.println("OK    DashboardService found " + dashboard);
    }

    @Override
    public Context getContext() {
        return context;
    }
}