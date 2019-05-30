package ch.heig.cashflow.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.CategorySelectActivity;
import ch.heig.cashflow.adapters.categories.CategoryAddExpenseAdapter;
import ch.heig.cashflow.adapters.categories.CategoryAddIncomeAdapter;
import ch.heig.cashflow.utils.ApplicationResources;

/**
 * Fragment tab host to display income and expense category list
 *
 * @author Aleksandar Milenkovic
 * @version 1.0
 * @see ch.heig.cashflow.fragments.CategoriesFragmentTabHost
 */
public class CategoriesFragmentTabHost extends Fragment {
    private FragmentTabHost fragmentTabHost;

    /**
     * Constructor
     */
    public CategoriesFragmentTabHost() {
    }

    /**
     * Singleton
     *
     * @return new instance
     */
    public static CategoriesFragmentTabHost newInstance() {
        return new CategoriesFragmentTabHost();
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
        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_category_tabhost, container, false);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     *
     * @param menu     The options menu in which you place your items.
     * @param inflater MenuInflater
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.category_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /**
     * This hook is called whenever an item in your options menu is selected
     *
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (item.getItemId() == R.id.actionbar_add_category) {
            Intent catSelect = new Intent(getContext(), CategorySelectActivity.class);
            int i = fragmentTabHost.getCurrentTab();

            if (i == 0) {
                catSelect.putExtra(getResources().getString(R.string.category_adapter_key), new CategoryAddExpenseAdapter());
            } else {
                catSelect.putExtra(getResources().getString(R.string.category_adapter_key), new CategoryAddIncomeAdapter());
            }
            startActivity(catSelect);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
        fragmentTabHost = view.findViewById(android.R.id.tabhost);
    }

    /**
     * Called when the fragment's activity has been created and this fragment's view hierarchy instantiated
     *
     * @param savedInstanceState If non-null, this fragment is being re-constructed from a previous
     *                           saved state as given here. This value may be null.
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle tab0 = new Bundle();
        tab0.putLong("index", 0);

        Bundle tab1 = new Bundle();
        tab1.putLong("index", 1);

        fragmentTabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("Expense").setIndicator("Expense"), CategoryFragment.class, tab0);
        fragmentTabHost.addTab(fragmentTabHost.newTabSpec("Income").setIndicator("Income"), CategoryFragment.class, tab1);
        decorateTabs();

        fragmentTabHost.setOnTabChangedListener(new FragmentTabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                decorateTabs();
            }
        });
    }

    /**
     * Color tabs according to their state
     */
    void decorateTabs() {
        ApplicationResources appRes = new ApplicationResources(getContext());
        int tab = fragmentTabHost.getCurrentTab();
        for (int i = 0; i < fragmentTabHost.getTabWidget().getChildCount(); i++) {
            // When tab is not selected
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(appRes.getColor(R.color.white));
            TextView tv = fragmentTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(appRes.getColor(R.color.black));
        }
        // When tab is selected
        fragmentTabHost.getTabWidget().getChildAt(fragmentTabHost.getCurrentTab()).setBackgroundColor(appRes.getColor(R.color.colorPrimary));
        TextView tv = fragmentTabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
        tv.setTextColor(appRes.getColor(R.color.white));
    }
}
