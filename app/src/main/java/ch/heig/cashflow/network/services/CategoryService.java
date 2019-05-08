package ch.heig.cashflow.network.services;

import com.google.gson.Gson;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;

public class CategoryService extends APIService {

    Callback callback;

    public CategoryService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    // GetOne : GET /api/categories/{id}
    public void get(long id) {
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.CATEGORIES + id);
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
        manager.execute(Config.CATEGORIES + category.getID());
    }

    // Update : PUT /api/categories/{id}
    public void enable(Category category) {
        category.setEnabled(true);
        update(category);
    }

    // Update : PUT /api/categories/{id}
    public void disable(Category category) {
        category.setEnabled(false);
        update(category);
    }

    // Delete : DELETE /api/categories/{id}
    public void delete(Category category) {
        new APIManager(this, true, APIManager.METHOD.DELETE).execute(Config.CATEGORIES + category.getID());
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (!checkResponse(result))
            return;

        switch (result.method) {
            case GET: // GetOne : GET /api/categories/{id}
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

    public interface Callback extends APIService.APICallback {

        void getFinished(Category category);

        void operationFinished(boolean isFinished);
    }
}
