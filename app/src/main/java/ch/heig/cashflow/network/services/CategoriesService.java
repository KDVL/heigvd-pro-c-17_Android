/**
 * Service used to retrieve all the categories through the API
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/GetAllCategories
 */

package ch.heig.cashflow.network.services;

import java.util.Arrays;
import java.util.List;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;
import ch.heig.cashflow.utils.Type;

public class CategoriesService extends APIService {

    private Callback callback;

    /**
     * The CategoriesService constructor
     *
     * @param callback The callback class
     */
    public CategoriesService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    /**
     * Get all the existing categories related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/categories
     */
    public void getAll() {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.CATEGORIES);
    }

    /**
     * Get all the existing categories by type related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/categories/type/{type}
     *
     * @param type The transaction {@code Type}
     */
    public void getType(Type type) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.CATEGORIES_TYPE + type);
    }

    /**
     * Retrieve the categories list and push it
     *
     * @param result The request result from APIManager
     */
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

    /**
     * The callback interface used by client
     */
    public interface Callback extends APICallback {
        void getFinished(List<Category> categories);
    }
}
