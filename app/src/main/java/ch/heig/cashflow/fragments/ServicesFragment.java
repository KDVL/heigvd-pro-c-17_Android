/**
 * Fragment used to perform unit tests
 *
 * @author Thibaud ALT
 * @version 1.0
 */

package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.models.Expense;
import ch.heig.cashflow.network.callbacks.CategoriesCallback;
import ch.heig.cashflow.network.callbacks.CategoryCallback;
import ch.heig.cashflow.network.callbacks.DashboardCallback;
import ch.heig.cashflow.network.callbacks.TransactionCallback;
import ch.heig.cashflow.network.callbacks.TransactionsCallback;
import ch.heig.cashflow.utils.Type;

public class ServicesFragment extends Fragment {

    private static final String TAG = ServicesFragment.class.getSimpleName();

    private boolean state;

    /**
     * The required ServicesFragment empty public constructor
     */
    public ServicesFragment() {
        // Required empty public constructor
    }

    /**
     * Create a new ServicesFragment instance
     *
     * @return ServicesFragment A new ServicesFragment instance
     */
    public static ServicesFragment newInstance() {
        return new ServicesFragment();
    }

    /**
     * Perform all the services test
     *
     * @param savedInstanceState The saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testDashboardService();
        testCategoriesService();
        testTransactionsService();
        testCategoryService();
        testTransactionService();
    }

    /**
     * Test the dashboard services
     *
     * @see ch.heig.cashflow.network.services.DashboardService
     */
    public void testDashboardService() {
        new DashboardCallback(getContext(), this).getAll();
        new DashboardCallback(getContext(), this).getAllByMonth();
    }

    /**
     * Test the categories services
     *
     * @see ch.heig.cashflow.network.services.CategoriesService
     */
    public void testCategoriesService() {
        new CategoriesCallback(getContext(), this).getAll();
        new CategoriesCallback(getContext(), this).getType(Type.EXPENSE);
    }

    /**
     * Test the transactions services
     *
     * @see ch.heig.cashflow.network.services.TransactionsService
     */
    public void testTransactionsService() {
        new TransactionsCallback(getContext(), this).getAll();
        new TransactionsCallback(getContext(), this).getAll(2019, 5);
        new TransactionsCallback(getContext(), this).getType(Type.EXPENSE);
        new TransactionsCallback(getContext(), this).getTypeByMonth(Type.INCOME);
    }

    /**
     * Test the category services
     *
     * @see ch.heig.cashflow.network.services.CategoryService
     */
    public void testCategoryService() {
        Category cat = new Category(1, "New", "icon", Type.EXPENSE, 123, true);
        new CategoryCallback(getContext(), this).get(5);
        new CategoryCallback(getContext(), this).add(cat);
        new CategoryCallback(getContext(), this).update(cat);
        new CategoryCallback(getContext(), this).delete(cat);
    }

    /**
     * Test the transactions services
     *
     * @see ch.heig.cashflow.network.services.TransactionService
     */
    public void testTransactionService() {
        Category cat = new Category(1, "New", "icon", Type.EXPENSE, 123, true);
        Expense expense = new Expense(5, "2019-05-01", cat, 123, "desc");
        new TransactionCallback(getContext(), this).get(5);
        new TransactionCallback(getContext(), this).add(expense);
        new TransactionCallback(getContext(), this).update(expense);
        new TransactionCallback(getContext(), this).delete(expense);
    }
}
