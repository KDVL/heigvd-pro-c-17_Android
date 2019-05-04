package ch.heig.cashflow.network;

import android.content.Context;

import com.google.gson.Gson;

import ch.heig.cashflow.models.Category;


public class CategoryService implements DownloadCallback<APIManager.Result> {

    private Callback callback;
    private Gson gson = new Gson();

    public CategoryService(Callback call, long id) {
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
            callback.connectionFailed(result.exception.toString());
            return;
        }

        switch (result.method) {
            case GET: // GetOne : GET /api/categories/{id}
                Gson gson = new Gson();
                callback.getFinished(gson.fromJson(result.resultString, Category.class));
                break;

            case POST:  // Add : POST /api/categories
                callback.addFinished(true);
                break;

            case PUT: // Update : PUT /api/categories/{id}
                callback.updateFinished(true);
                break;

            case DELETE: // Delete : DELETE /api/categories/{id}
                callback.deleteFinished(true);
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

        void addFinished(boolean isAdded);

        void updateFinished(boolean isUpdated);

        void deleteFinished(boolean isDeleted);
    }
}
