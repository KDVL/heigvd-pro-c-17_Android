/**
 * Fragment to display income or expense category recycler view list
 *
 * @author Aleksandar MILENKOVIC
 * @version 1.0
 * @see ch.heig.cashflow.fragments.CategoryFragment
 */

package ch.heig.cashflow.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ch.heig.cashflow.R;
import ch.heig.cashflow.adapters.cards.CategoryFragmentAdapter;
import ch.heig.cashflow.models.Category;
import ch.heig.cashflow.network.services.CategoriesService;
import ch.heig.cashflow.network.services.CategoryService;
import ch.heig.cashflow.utils.ApplicationResources;
import ch.heig.cashflow.utils.DividerItemDecoration;
import ch.heig.cashflow.utils.Type;

public class CategoryFragment extends Fragment implements CategoriesService.Callback,
        CategoryService.Callback {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    private ApplicationResources appRes;

    private CategoriesService css;
    private CategoryService cs;
    private List<Category> categoriesList;
    private RecyclerView recyclerView;

    private View view;

    private Long tabId;

    /**
     * Constructor
     */
    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Called to have the fragment instantiate its user interface view
     *
     * @param inflater           The LayoutInflater object that can be used to inflate any views in the fragment
     * @param container          If non-null, this is the parent view that the fragment's UI should be attached to.
     *                           The fragment should not add the view itself, but this can be used to generate
     *                           the LayoutParams of the view. This value may be null.
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous saved state as given here
     * @return Return the View for the fragment's UI, or null.
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");

        view = inflater.inflate(R.layout.fragment_category, container, false);

        Bundle b = getArguments();
        tabId = b.getLong("index");

        categoriesList = new ArrayList<>();

        appRes = new ApplicationResources(getContext());

        css = new CategoriesService(this);
        cs = new CategoryService(this);

        // Lookup the recyclerview in activity layout
        recyclerView = view.findViewById(R.id.cat_type_recyclerview);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), R.drawable.divider));

        // Call list of category from server
        updateListeFromServer(tabId);

        return view;
    }

    /**
     * Adjust the list according to the selected tab
     *
     * @param tabId id of tab
     */
    private void updateListeFromServer(Long tabId) {
        if (tabId == 0)
            css.getType(Type.EXPENSE);
        else
            css.getType(Type.INCOME);
    }

    /**
     * Called immediately after onCreateView
     *
     * @param view               The View returned by onCreateView
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here. This value may be null.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Synchronized method to handle spam button
     *
     * @param categories list of categories
     */
    private synchronized void refresh(List<Category> categories) {
        categoriesList = categories;
        // Create adapter passing in the sample user data
        CategoryFragmentAdapter adapter = new CategoryFragmentAdapter(this, view.getContext(), categoriesList, tabId);
        // Attach the adapter to the recyclerview to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        // That's all!
    }

    /**
     * Return off call API GETALL
     *
     * @param categories list of category
     */
    @Override
    public void getFinished(List<Category> categories) {
        refresh(categories);
    }

    /**
     * Return off call API GET
     *
     * @param category category
     */
    @Override
    public void getFinished(Category category) {

    }

    /**
     * Return off call API POST, PUT and DELETE
     *
     * @param isFinished state of request
     */
    @Override
    public void operationFinished(boolean isFinished) {
        if (isFinished) {
            Toast.makeText(getContext(), appRes.getString(R.string.server_response_ok), Toast.LENGTH_SHORT).show();
            updateListeFromServer(tabId);
        }
    }

    /**
     * Return fail
     *
     * @param error error string
     */
    @Override
    public void connectionFailed(String error) {
        if(getContext() != null)
            Toast.makeText(getContext(), appRes.getString(R.string.server_response_nok), Toast.LENGTH_SHORT).show();
    }
}
