/**
 * A categories callback used to perform unit tests
 *
 * @author Thibaud ALT
 * @version 1.0
 * @see ch.heig.cashflow.network.services.CategoriesService
 */

package ch.heig.cashflow.network.callbacks;

import android.content.Context;

import java.util.List;

import ch.heig.cashflow.fragments.ServicesFragment;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.utils.Type;

public class CategoriesCallback implements CategoriesService.Callback {

    private Context context;
    private ServicesFragment fragment;
    private CategoriesService cs;

    /**
     * The CategoriesCallback constructor
     *
     * @param context  The application context
     * @param fragment The linked fragment
     */
    public CategoriesCallback(Context context, ServicesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.cs = new CategoriesService(this);
    }

    /**
     * Get all the existing categories related to a registered {@code User}
     */
    public void getAll() {
        cs.getAll();
    }

    /**
     * Get all the existing categories by type related to a registered {@code User}
     *
     * @param type The transaction {@code Type}
     */
    public void getType(Type type) {
        cs.getType(type);
    }

    /**
     * Display an error when the API connection failed
     *
     * @param error
     */
    @Override
    public void connectionFailed(String error) {
        System.err.println("DOWN  - CategoriesService " + error);
    }

    /**
     * Display a message about the categories list status
     *
     * @param categories The categories list
     */
    @Override
    public void getFinished(List<Category> categories) {
        if (categories.size() < 1)
            System.err.println("EMPTY LIST - CategoriesService");
        else
            System.out.println("OK    CategoriesService found " + categories);
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
