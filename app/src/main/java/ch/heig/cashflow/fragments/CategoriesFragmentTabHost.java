package ch.heig.cashflow.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import ch.heig.cashflow.R;
import ch.heig.cashflow.activites.CategorySelectActivity;
import ch.heig.cashflow.adapters.categories.CategoryAddExpenseAdapter;
import ch.heig.cashflow.adapters.categories.CategoryAddIncomeAdapter;
import ch.heig.cashflow.utils.SimpleColor;

public class CategoriesFragmentTabHost extends Fragment {

    private static final String TAG = CategoriesFragmentTabHost.class.getSimpleName();
    private FragmentTabHost fragmentTabHost;
    private SimpleColor sc = new SimpleColor(getContext());

    public CategoriesFragmentTabHost() {
    }

    public static CategoriesFragmentTabHost newInstance() {
        return new CategoriesFragmentTabHost();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView: ");

        setHasOptionsMenu(true);

        return inflater.inflate(R.layout.fragment_category_tabhost, container, false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.category_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.actionbar_add_category:
                Intent catSelect = new Intent(getContext(), CategorySelectActivity.class);
                int i = fragmentTabHost.getCurrentTab();
                String str = "N° tab = " + i; /** TAB OK */
                if (i == 0) {
                    Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    catSelect.putExtra(getResources().getString(R.string.category_adapter_key), new CategoryAddExpenseAdapter());
                } else {
                    Toast.makeText(getActivity().getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                    catSelect.putExtra(getResources().getString(R.string.category_adapter_key), new CategoryAddIncomeAdapter());
                }
                startActivity(catSelect);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i(TAG, "onViewCreated: ");
        fragmentTabHost = view.findViewById(android.R.id.tabhost);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated: ");

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

    void decorateTabs() {
        int tab = fragmentTabHost.getCurrentTab();
        for (int i = 0; i < fragmentTabHost.getTabWidget().getChildCount(); i++) {
            // When tab is not selected
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFFFFF"));
            TextView tv = (TextView) fragmentTabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(Color.BLACK);
        }
        // When tab is selected
        fragmentTabHost.getTabWidget().getChildAt(fragmentTabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#708090"));
        TextView tv = (TextView) fragmentTabHost.getTabWidget().getChildAt(tab).findViewById(android.R.id.title);
        tv.setTextColor(Color.WHITE);
    }
}
