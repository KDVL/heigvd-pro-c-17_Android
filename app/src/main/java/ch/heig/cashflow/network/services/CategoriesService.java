package ch.heig.cashflow.network.services;

import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Type;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;

public class CategoriesService extends APIService {

    Callback callback;

    public CategoriesService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    // GetAll : GET /api/categories
    public void getAll() {
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.CATEGORIES);
    }

    // PerType : GET /api/categories/type/{type}
    public void getType(Type type) {
        new APIManager(this, true, APIManager.METHOD.GET).execute(Config.CATEGORIES_TYPE + type);
    }

    @Override
    public void updateFromDownload(APIManager.Result result) {

        if (!checkResponse(result))
            return;

        Category[] categories;
        categories = gson.fromJson(result.resultString, Category[].class);

        if (result.tag.contains(Config.CATEGORIES_TYPE))
            callback.getFinished(Arrays.asList(categories));
        else if (result.tag.contains(Config.CATEGORIES))
            callback.getFinished(Arrays.asList(categories));

    }

    public interface Callback extends APICallback {
        void getFinished(List<Category> categories);
    }
}
