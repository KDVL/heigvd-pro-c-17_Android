package ch.heig.cashflow.network.services;

import android.content.Context;

import com.google.gson.Gson;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.callbacks.BaseCallback;
import ch.heig.cashflow.network.callbacks.DownloadCallback;
import ch.heig.cashflow.network.utils.Config;

public class CategoryService implements DownloadCallback<APIManager.Result> {

    private Callback callback;
    private Gson gson = new Gson();

    public CategoryService(Callback call) {
        callback = call;
    }

    // GetOne : GET /api/categories/{id}
    public void get(long id) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.CATEGORY + id);
    }

    // Add : POST /api/categories
    public void add(Category category) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.POST);
        manager.setPostParams(gson.toJson(category, Category.class));
        manager.execute(Config.CATEGORIES);
    }

    // Update : PUT /api/categories/{id}
    public void update(Category category) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.PUT);
        manager.setPostParams(gson.toJson(category, Category.class));
        manager.execute(Config.CATEGORY + category.getID());
    }

    // Delete : DELETE /api/categories/{id}
    public void delete(Category category) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.DELETE);
        manager.execute(Config.CATEGORY + category.getID());
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (result.responseCode != 200) {
            String exception = result.exception == null ? "" : result.exception.toString();
            callback.connectionFailed(exception);
            return;
        }

        switch (result.method) {
            case GET: // GetOne : GET /api/categories/{id}
                Gson gson = new Gson();
                callback.getFinished(gson.fromJson(result.resultString, Category.class));
                break;

            case POST:  // Add : POST /api/categories
                callback.operationFinished(true);
                break;

            case PUT: // Update : PUT /api/categories/{id}
                callback.operationFinished(true);
                break;

            case DELETE: // Delete : DELETE /api/categories/{id}
                callback.operationFinished(true);
                break;
        }
    }

    @Override
    public Context getContext() {
        return callback.getContext();
    }

    public interface Callback extends BaseCallback {
        void connectionFailed(String error);

        void getFinished(Category category);

        void operationFinished(boolean isFinished);
    }
}
