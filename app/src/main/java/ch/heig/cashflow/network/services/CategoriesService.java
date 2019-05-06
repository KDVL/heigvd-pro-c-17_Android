package ch.heig.cashflow.network.services;

import android.content.Context;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;

public class CategoriesService implements DownloadCallback<APIManager.Result> {

    private Callback callback;
    private Gson gson = new Gson();

    public CategoriesService(Callback call) {
        callback = call;
    }

    // GetAll : GET /api/categories
    public void getAll() {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.CATEGORIES);
    }

    // PerType : GET /api/categories/type/{type}
    public void getType(Type type) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.CATEGORIES_TYPE + type);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        Gson gson = new Gson();
        Category[] categories;

        if (result.responseCode != 200) {
            String exception = result.exception == null ? "" : result.exception.toString();
            callback.connectionFailed(exception);
            return;
        }

        categories = gson.fromJson(result.resultString, Category[].class);

        if (result.tag.contains(Config.CATEGORIES_TYPE)) {
            /** MODIF ALEKS POUT TABLEAU NULL SI NOUVEAU UTILISATEUR */
            if (categories != null) {
                callback.getFinished(Arrays.asList(categories));
            } else {
                callback.getFinished(new ArrayList<Category>());
            }
        } else if (result.tag.contains(Config.CATEGORIES)) {
            if (categories != null) {
                callback.getFinished(Arrays.asList(categories));
            } else {
                callback.getFinished(new ArrayList<Category>());
            }
        }
    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback {
        void connectionFailed(String error);

        void getFinished(List<Category> categories);
    }
}
