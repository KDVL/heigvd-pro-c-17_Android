package ch.heig.cashflow.network.services;

import android.content.Context;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Budget;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;

public class BudgetsService implements DownloadCallback<APIManager.Result> {

    private Callback callback;
    private Gson gson = new Gson();

    public BudgetsService(Callback call) {
        callback = call;
    }

    // GetAll : GET /api/budgets
    public void getAll() {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.BUDGETS);
    }

    // PerType : GET /api/budgets/YYYY/MM
    public void getMonth(String year, String month) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.BUDGET + year + "/" + month);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        Gson gson = new Gson();
        Budget[] budgets;

        if (result.responseCode != 200) {
            String exception = result.exception == null ? "" : result.exception.toString();
            callback.connectionFailed(exception);
            return;
        }

        budgets = gson.fromJson(result.resultString, Budget[].class);
        switch (result.tag) {
            case Config.BUDGETS: // GetAll : GET /api/categories
                callback.getAllFinished(Arrays.asList(budgets));
                break;
            case Config.BUDGET: // PerType : GET /api/categories/type/{type}
                callback.getMonthFinished(gson.fromJson(result.resultString, Budget.class));
                break;
        }

    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback {
        void connectionFailed(String error);

        void getAllFinished(List<Budget> budgets);

        void getMonthFinished(Budget budget);
    }
}
