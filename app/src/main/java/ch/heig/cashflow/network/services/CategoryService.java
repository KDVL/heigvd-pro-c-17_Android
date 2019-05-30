/**
 * Service used to manage a specific category through the API
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/GetOneCategory
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/AddCategory
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/UpdateCategory
 * @see https://github.com/Enophi/heigvd-pro-c-17/wiki/DisableCategory
 */

package ch.heig.cashflow.network.services;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.APIManager;
import ch.heig.cashflow.network.utils.Config;

public class CategoryService extends APIService {

    private Callback callback;

    /**
     * The CategoryService constructor
     *
     * @param callback The callback class
     */
    public CategoryService(Callback callback) {
        super(callback);
        this.callback = callback;
    }

    /**
     * Get one category related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: GET
     * REST API URI: /api/categories/{id}
     *
     * @param id The category id
     */
    public void get(long id) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.GET);
        manager.execute(Config.CATEGORIES + id);
    }

    /**
     * Add a new category related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: POST
     * REST API URI: /api/categories
     *
     * @param category The {@code Category}
     */
    public void add(Category category) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.POST);
        manager.setPostParams(gson.toJson(category, Category.class));
        manager.execute(Config.CATEGORIES);
    }

    /**
     * Update a category related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: PUT
     * REST API URI: /api/categories/{id}
     *
     * @param category The {@code Category}
     */
    public void update(Category category) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.PUT);
        manager.setPostParams(gson.toJson(category, Category.class));
        manager.execute(Config.CATEGORIES + category.getID());
    }

    /**
     * Enable a category related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: PUT
     * REST API URI: /api/categories/{id}
     *
     * @param category The {@code Category}
     */
    public void enable(Category category) {
        category.setEnabled(true);
        update(category);
    }

    /**
     * Disable a category related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: PUT
     * REST API URI: /api/categories/{id}
     *
     * @param category The {@code Category}
     */
    public void disable(Category category) {
        category.setEnabled(false);
        update(category);
    }

    /**
     * Disable a transaction related to a registered {@code User}
     *
     * <p>
     * REST API METHOD: DELETE
     * REST API URI: /api/categories/{id}
     *
     * @param category The {@code Category}
     */
    public void delete(Category category) {
        APIManager manager = new APIManager(this, true, APIManager.METHOD.DELETE);
        manager.execute(Config.CATEGORIES + category.getID());
    }

    /**
     * Set the operation status received from the API response
     *
     * @param result The request result from APIManager
     */
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

    /**
     * The callback interface used by client
     */
    public interface Callback extends APIService.APICallback {

        void getFinished(Category category);

        void operationFinished(boolean isFinished);
    }
}
