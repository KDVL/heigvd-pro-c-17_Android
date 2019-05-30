/**
 * A categorx callback used to perform unit tests
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see ch.heig.cashflow.network.services.CategoryService
 */

package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoryService;

public class CategoryCallback implements CategoryService.Callback {

    private Context context;
    private ServicesFragment fragment;
    private CategoryService cs;

    /**
     * The CategoryCallback constructor
     *
     * @param context  The application context
     * @param fragment The linked fragment
     */
    public CategoryCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.cs = new CategoryService(this);
    }

    /**
     * Get one category related to a registered {@code User}
     *
     * @param id The category id
     */
    public void get(long id) {
        cs.get(id);
    }

    /**
     * Add a new category related to a registered {@code User}
     *
     * @param category The {@code Category}
     */
    public void add(Category category) {
        cs.add(category);
    }

    /**
     * Update a category related to a registered {@code User}
     *
     * @param category The {@code Category}
     */
    public void update(Category category) {
        cs.update(category);
    }

    /**
     * Disable a transaction related to a registered {@code User}
     *
     * @param category The {@code Category}
     */
    public void delete(Category category) {
        cs.delete(category);
    }

    /**
     * Display an error when the API connection failed
     *
     * @param error
     */
    @Override
    public void connectionFailed(String error) {
        System.err.println("DOWN  - CategoryService " + error);
    }

    /**
     * Display a success message on operation finished
     *
     * @param isFinished The operation status
     */
    @Override
    public void operationFinished(boolean isFinished) {
        System.out.println("OK    CategoryService found");
    }

    /**
     * Display a message about the category status
     *
     * @param category The categories list
     */
    @Override
    public void getFinished(Category category) {
        if (category == null)
            System.err.println("EMPTY - CategoryService");
        else
            System.out.println("OK    CategoryService found " + category);
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
